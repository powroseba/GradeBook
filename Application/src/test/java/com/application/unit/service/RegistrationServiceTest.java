package com.application.unit.service;

import com.application.exceptions.AuthModelUsernameValidationException;
import com.application.exceptions.notfound.SchoolClassNotFound;
import com.application.exceptions.UserAlreadyExistException;
import com.application.service.implementations.EmailService;
import com.application.service.implementations.RegistrationServiceImpl;
import com.domain.AuthModel;
import com.entities.*;
import com.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserModelRepository userModelRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    public void signUpTeacher() throws Exception {
        List<Object> models = getModels(UserRole.TEACHER);
        AuthModel authModel = (AuthModel) models.get(0);
        UserModel userModel = (UserModel) models.get(1);
        Teacher teacher = (Teacher) models.get(2);
        Exercise exercise = (Exercise) models.get(3);

        when(userModelRepository.save(any(UserModel.class))).thenReturn(userModel);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        registrationService.signUp(authModel);

        assertNotNull(authModel);
        assertNotNull(userModel);
        assertNotNull(teacher);
        assertNotNull(exercise);

        assertNotNull(userModelRepository.save(any(UserModel.class)));
        assertNotNull(teacherRepository.save(any(Teacher.class)));
        assertNotNull(exerciseRepository.save(any(Exercise.class)));

        assertThat(userModel.getUserModelDetails()).isEqualTo(teacher);
        assertThat(teacher.getUserModel()).isEqualTo(userModel);

        assertThat(teacher.getExercises()).containsExactly(exercise);
        assertThat(exercise.getTeacher()).isEqualTo(teacher);

        assertThat(authModel.getDateOfBirth()).isEqualToIgnoringMinutes(teacher.getDateOfBirth());
        assertThat(authModel.getFirstName()).isEqualTo(teacher.getFirstName());
        assertThat(authModel.getLastName()).isEqualTo(teacher.getLastName());
        assertThat(authModel.getEmail()).isEqualTo(userModel.getEmail());
    }

    @Test(expected = AuthModelUsernameValidationException.class)
    public void signUpExpectAuthModelValidationException() throws Exception {
        AuthModel authModel = (AuthModel) getModels(UserRole.TEACHER).get(0);
        authModel.setEmail("a@mail.com");

        registrationService.signUp(authModel);

        assertNotNull(authModel);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void signUpExpectUserAlreadyExistException() throws Exception {
        AuthModel authModel = (AuthModel) getModels(UserRole.TEACHER).get(0);
        when(userModelRepository.findByEmail(authModel.getEmail())).thenReturn((Optional<UserModel>) getModels(UserRole.TEACHER).get(1));
        registrationService.signUp(authModel);

        assertNotNull(userModelRepository.findByEmail(authModel.getEmail()));
    }

    @Test
    public void signUpStudent() throws Exception {
        List<Object> models = getModels(UserRole.STUDENT);
        AuthModel authModel = (AuthModel) models.get(0);
        UserModel userModel = (UserModel) models.get(1);
        Student student = (Student) models.get(5);
        Optional<SchoolClass> schoolClass = (Optional<SchoolClass>) models.get(4);
        schoolClass.get().getExercises().add((Exercise)models.get(3));

        when(userModelRepository.save(any(UserModel.class))).thenReturn(userModel);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(schoolClassRepository.findByName(authModel.getSchoolClassName())).thenReturn(schoolClass);

        registrationService.signUp(authModel);

        assertNotNull(authModel);
        assertNotNull(userModel);
        assertNotNull(student);
        assertNotNull(schoolClass);

        assertNotNull(userModelRepository.save(any(UserModel.class)));
        assertNotNull(studentRepository.save(any(Student.class)));
        assertNotNull(schoolClassRepository.findByName(authModel.getSchoolClassName()));

        assertThat(schoolClass.get().getExercises()).isNotEmpty();
        assertThat(userModel.getUserModelDetails()).isEqualTo(student);
        assertThat(student.getUserModel()).isEqualTo(userModel);

        assertThat(student.getSchoolClass()).isEqualTo(schoolClass);
        assertThat(schoolClass.get().getStudents()).containsExactly(student);

        assertThat(student.getExercises()).isEqualTo(schoolClass.get().getExercises());
        assertThat(schoolClass.get().getExercises()).isEqualTo(student.getExercises());

        assertThat(authModel.getDateOfBirth()).isEqualToIgnoringMinutes(student.getDateOfBirth());
        assertThat(authModel.getFirstName()).isEqualTo(student.getFirstName());
        assertThat(authModel.getLastName()).isEqualTo(student.getLastName());
        assertThat(authModel.getEmail()).isEqualTo(userModel.getEmail());
    }

    @Test(expected = SchoolClassNotFound.class)
    public void singUpExpectSchoolClassNotFoundException() throws Exception {
        AuthModel authModel = (AuthModel) getModels(UserRole.STUDENT).get(0);
        when(schoolClassRepository.findByName(authModel.getSchoolClassName())).thenReturn(null);
        registrationService.signUp(authModel);

        assertNull(schoolClassRepository.findByName(authModel.getSchoolClassName()));
    }

    public List<Object> getModels(UserRole role) {
        AuthModel authModel = new AuthModel("someemail@mail.com","firstname",
                "lastname", role.name(), new Date());
        authModel.setExercise(Exercises.BIOLOGY.name());
        authModel.setSchoolClassName("1a");

        UserModel userModel = new UserModel(authModel.getEmail(), "someemail", "password", authModel.getRole());
        Teacher teacher = new Teacher(authModel.getFirstName(), authModel.getLastName(), authModel.getDateOfBirth());
        Exercise exercise = new Exercise(Exercises.valueOf(authModel.getExercise()));
        SchoolClass schoolClass = new SchoolClass("1a", Profile.ANGELIAN);
        Student student = new Student(authModel.getFirstName(), authModel.getLastName(), authModel.getDateOfBirth());

        return Arrays.asList(authModel, userModel, teacher, exercise, schoolClass, student);
    }
}
