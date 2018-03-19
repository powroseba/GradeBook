package com.application.unit.service;

import com.application.TokenGenerator;
import com.application.exceptions.notfound.ExerciseNotFoundException;
import com.application.exceptions.notfound.TeacherNotFoundException;
import com.application.service.implementations.ExerciseServiceImpl;
import com.entities.*;
import com.repositories.ExerciseRepository;
import com.repositories.UserModelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private UserModelRepository userModelRepository;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    private MockHttpServletRequest request;
    private String username;

    @Before
    public void before() {
        TokenGenerator tokenGenerator = new TokenGenerator();
        this.request = new MockHttpServletRequest();
        this.username = "teacher";
        this.request.addHeader("Authorization", tokenGenerator.generate(1000, username, UserRole.TEACHER));
    }

    @Test(expected = TeacherNotFoundException.class)
    public void getTeacherExercisesExpectNotFoundTeacherTest() throws Exception {
        when(userModelRepository.findByUsername(username)).thenReturn(new UserModel());

        exerciseService.getTeacherExercises(request);

        assertNull(userModelRepository.findByUsername(username));
    }

    @Test(expected = ExerciseNotFoundException.class)
    public void getTeacherExerciseExpectExerciseNotFoundTest() throws Exception {
        when(userModelRepository.findByUsername(username)).thenReturn(getExampleUserModel());
        when(exerciseRepository.findByTeacher(any())).thenReturn(null);

        exerciseService.getTeacherExercises(request);

        assertNull(exerciseRepository.findByTeacher(any()));
    }

    @Test
    public void getTeacherExercisesTest() throws Exception {
        when(userModelRepository.findByUsername(username)).thenReturn(getExampleUserModel());
        Teacher t = (Teacher) getExampleUserModel().getUserModelDetails();
        List<Exercise> exercises = (List<Exercise>) ((Teacher) getExampleUserModel().getUserModelDetails()).getExercises();
        when(exerciseRepository.findByTeacher(t)).thenReturn(exercises);

        exerciseService.getTeacherExercises(request);

        assertNotNull(t);
        assertNotNull(t);
    }

    public UserModel getExampleUserModel() {
        UserModel userModel = new UserModel("teacher@mail.com", "teacher", "password", UserRole.TEACHER.name());
        Teacher teacher = new Teacher("firstname", "lastname", new Date());
        List<Exercise> exercises = Arrays.asList(new Exercise(Exercises.BIOLOGY), new Exercise(Exercises.ENGLISH));

        teacher.getExercises().add(exercises.get(0));
        teacher.getExercises().add(exercises.get(1));
        exercises.get(0).setTeacher(teacher);
        exercises.get(1).setTeacher(teacher);

        teacher.setUserModel(userModel);
        userModel.setUserDetails(teacher);

        return userModel;
    }
}
