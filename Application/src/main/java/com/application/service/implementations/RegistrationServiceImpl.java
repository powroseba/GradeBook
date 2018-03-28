package com.application.service.implementations;

import com.application.exceptions.AuthModelUsernameValidationException;
import com.application.exceptions.notfound.SchoolClassNotFound;
import com.application.exceptions.UserAlreadyExistException;
import com.application.service.RegistrationService;
import com.application.tools.RandomStringGenerator;
import com.domain.AuthModel;
import com.domain.MailProperties;
import com.entities.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private EmailService emailService;

    private SchoolClassRepository schoolClassRepository;

    private ExerciseRepository exerciseRepository;

    private TeacherRepository teacherRepository;

    private StudentRepository studentRepository;

    private UserModelRepository userModelRepository;

    @Autowired
    public RegistrationServiceImpl(EmailService emailService, SchoolClassRepository schoolClassRepository,
                                   ExerciseRepository exerciseRepository, TeacherRepository teacherRepository,
                                   StudentRepository studentRepository, UserModelRepository userModelRepository) {
        this.emailService = emailService;
        this.schoolClassRepository = schoolClassRepository;
        this.exerciseRepository = exerciseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.userModelRepository = userModelRepository;
    }

    @Override
    public void signUp(AuthModel authModel) {


        String username = authModel.getEmail().split("@")[0];
        if (username.length() < 4) {
            throw new AuthModelUsernameValidationException();
        }

        if (Optional.ofNullable(userModelRepository.findByEmail(authModel.getEmail())).isPresent()
                || Optional.ofNullable(userModelRepository.findByUsername(username)).isPresent()) {
            throw new UserAlreadyExistException();
        }
        String password = RandomStringGenerator.generate(16);
        switch (authModel.getRole()) {
            case "TEACHER" : {
                UserModel userModel = new UserModel(authModel.getEmail(), username, password, authModel.getRole());
                Teacher teacher = new Teacher(authModel.getFirstName(), authModel.getLastName(), authModel.getDateOfBirth());
                Exercise exercise = new Exercise(Exercises.valueOf(authModel.getExercise()));

                userModel = userModelRepository.save(userModel);
                teacher = teacherRepository.save(teacher);
                exercise = exerciseRepository.save(exercise);

                userModel.setUserDetails(teacher);
                teacher.setUserModel(userModel);

                teacher.getExercises().add(exercise);
                exercise.setTeacher(teacher);

                userModelRepository.save(userModel);
                teacherRepository.save(teacher);
                exerciseRepository.save(exercise);
                break;

            }
            case "STUDENT": {
                SchoolClass schoolClass = schoolClassRepository.findByName(authModel.getSchoolClassName()).get();
                if (!Optional.ofNullable(schoolClass).isPresent()) {
                    throw new SchoolClassNotFound();
                }
                UserModel userModel = new UserModel(authModel.getEmail(), username, password, authModel.getRole());
                Student student = new Student(authModel.getFirstName(), authModel.getLastName(), authModel.getDateOfBirth());

                userModel = userModelRepository.save(userModel);
                student = studentRepository.save(student);

                userModel.setUserDetails(student);
                student.setUserModel(userModel);

                student.setSchoolClass(schoolClass);
                schoolClass.getStudents().add(student);

                if (!schoolClass.getExercises().isEmpty()) {
                    for (Exercise e : schoolClass.getExercises()) {
                        student.getExercises().add(e);
                        e.getStudents().add(student);

                        exerciseRepository.save(e);
                    }
                }

                userModelRepository.save(userModel);
                studentRepository.save(student);
                schoolClassRepository.save(schoolClass);

                break;
            }
        }
        String content = "<b>Hello " + username + "</b><br>";
        content += "<br>Thanks for your registration in our service, try to login on your account.<br><p style=\"padding-left: 10px;\">" +
                "username: " + username + "<br><p style=\"padding-left: 10px;\">password: " + password;
        content += "<br><br><a href=\"localhost:8080/login\">Sign in here</a>";

        MailProperties mailProperties = new MailProperties(authModel.getEmail(), "Thanks for registration in GradeBook", content);
        emailService.sendPlainText(mailProperties);
    }
}
