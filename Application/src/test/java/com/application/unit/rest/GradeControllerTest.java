package com.application.unit.rest;

import com.application.Application;
import com.application.ConvertJavaObjectToJson;
import com.application.TokenGenerator;
import com.application.exceptions.notfound.*;
import com.application.service.GradeService;
import com.application.service.implementations.RabbitProducer;
import com.domain.ExerciseAndGrades;
import com.entities.Exercises;
import com.entities.Grade;
import com.entities.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class GradeControllerTest {

    @Autowired
    private MockMvc mvc;

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private String token = "";

    @Before
    public void generateToken() {
        this.token = tokenGenerator.generate(10, "username", UserRole.STUDENT);
    }

    @MockBean
    private GradeService gradeService;

    @MockBean
    private RabbitProducer rabbitProducer;

    @Test
    public void getMyGrades() throws Exception {

        when(gradeService.findGradesForStudent(any())).thenReturn(getExampleGrades());

        mvc.perform(get("/grades").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.email", is(getExampleGrades().getEmail())));
        verify(gradeService, times(1)).findGradesForStudent(any());
        verifyNoMoreInteractions(gradeService);
    }

    @Test
    public void getMyGradesExpectNotFoundUser() throws Exception {
        when(gradeService.findGradesForStudent(any())).thenThrow(new UserNotFoundException());

        mvc.perform(get("/grades").header("Authorization", token))
                .andExpect(status().isNotFound());
        verify(gradeService, times(1)).findGradesForStudent(any());
        verifyNoMoreInteractions(gradeService);
    }

    @Test
    public void getMyGradesExpectNotFoundGrades() throws Exception {
        when(gradeService.findGradesForStudent(any())).thenThrow(new GradeNotFoundException());

        mvc.perform(get("/grades").header("Authorization", token))
                .andExpect(status().isNotFound());
        verify(gradeService, times(1)).findGradesForStudent(any());
        verifyNoMoreInteractions(gradeService);
    }

    @Test
    public void getMyGradesExpectForbidden() throws Exception {
        String invalidToken = tokenGenerator.generate(10, "username", UserRole.TEACHER);
        mvc.perform(get("/grades").header("Authorization", invalidToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addGradeTest() throws Exception {
        token = tokenGenerator.generate(10, "teacher", UserRole.TEACHER);
        Grade grade = new Grade(new Date(), 2, "description");
        Exercises exercises = Exercises.BIOLOGY;

        ArgumentCaptor<Grade> argGrade = ArgumentCaptor.forClass(Grade.class);
        ArgumentCaptor<Exercises> argExercises = ArgumentCaptor.forClass(Exercises.class);
        ArgumentCaptor<Long> argId = ArgumentCaptor.forClass(Long.class);

        doNothing().when(gradeService).addGrade(any(), argId.capture(), argExercises.capture(), argGrade.capture());


        mvc.perform(post("/grades/add").header("Authorization", token)
                .param("studentId","1")
                .param("exercise", exercises.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(grade)))
                .andExpect(status().isCreated());
        verify(gradeService, times(1)).addGrade(any(),argId.capture(), argExercises.capture(), argGrade.capture());
        verifyNoMoreInteractions(gradeService);
    }

    @Test
    public void addGradeExpectTeacherNotFound() throws Exception {
        token = tokenGenerator.generate(10, "teacher", UserRole.TEACHER);
        Grade grade = new Grade(new Date(), 2, "description");
        Exercises exercises = Exercises.BIOLOGY;

        ArgumentCaptor<Grade> argGrade = ArgumentCaptor.forClass(Grade.class);
        ArgumentCaptor<Exercises> argExercises = ArgumentCaptor.forClass(Exercises.class);
        ArgumentCaptor<Long> argId = ArgumentCaptor.forClass(Long.class);

        doThrow(new TeacherNotFoundException()).when(gradeService).
                addGrade(any(), argId.capture(), argExercises.capture(), argGrade.capture());


        mvc.perform(post("/grades/add").header("Authorization", token)
                .param("studentId","1")
                .param("exercise", exercises.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(grade)))
                .andExpect(status().isNotFound());
        verify(gradeService, times(1)).addGrade(any(),argId.capture(), argExercises.capture(), argGrade.capture());
        verifyNoMoreInteractions(gradeService);
    }

    @Test
    public void addGradeExpectStudentNotFound() throws Exception {
        token = tokenGenerator.generate(10, "teacher", UserRole.TEACHER);
        Grade grade = new Grade(new Date(), 2, "description");
        Exercises exercises = Exercises.BIOLOGY;

        ArgumentCaptor<Grade> argGrade = ArgumentCaptor.forClass(Grade.class);
        ArgumentCaptor<Exercises> argExercises = ArgumentCaptor.forClass(Exercises.class);
        ArgumentCaptor<Long> argId = ArgumentCaptor.forClass(Long.class);

        doThrow(new StudentNotFoundException()).when(gradeService).
                addGrade(any(), argId.capture(), argExercises.capture(), argGrade.capture());


        mvc.perform(post("/grades/add").header("Authorization", token)
                .param("studentId","1")
                .param("exercise", exercises.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(grade)))
                .andExpect(status().isNotFound());
        verify(gradeService, times(1)).addGrade(any(),argId.capture(), argExercises.capture(), argGrade.capture());
        verifyNoMoreInteractions(gradeService);
    }

    @Test
    public void addGradeExpectForbidden() throws Exception {
        Grade grade = new Grade(new Date(), 2, "description");
        Exercises exercises = Exercises.BIOLOGY;

        ArgumentCaptor<Grade> argGrade = ArgumentCaptor.forClass(Grade.class);
        ArgumentCaptor<Exercises> argExercises = ArgumentCaptor.forClass(Exercises.class);
        ArgumentCaptor<Long> argId = ArgumentCaptor.forClass(Long.class);

        doNothing().when(gradeService).addGrade(any(), argId.capture(), argExercises.capture(), argGrade.capture());


        mvc.perform(post("/grades/add").header("Authorization", token)
                .param("studentId","1")
                .param("exercise", exercises.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(grade)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addGradeExpectExerciseNotFound() throws Exception {
        token = tokenGenerator.generate(10, "teacher", UserRole.TEACHER);
        Grade grade = new Grade(new Date(), 2, "description");
        Exercises exercises = Exercises.BIOLOGY;

        ArgumentCaptor<Grade> argGrade = ArgumentCaptor.forClass(Grade.class);
        ArgumentCaptor<Exercises> argExercises = ArgumentCaptor.forClass(Exercises.class);
        ArgumentCaptor<Long> argId = ArgumentCaptor.forClass(Long.class);

        doThrow(new ExerciseNotFoundException()).when(gradeService).
                addGrade(any(), argId.capture(), argExercises.capture(), argGrade.capture());


        mvc.perform(post("/grades/add").header("Authorization", token)
                .param("studentId","1")
                .param("exercise", exercises.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(grade)))
                .andExpect(status().isNotFound());
        verify(gradeService, times(1)).addGrade(any(),argId.capture(), argExercises.capture(), argGrade.capture());
        verifyNoMoreInteractions(gradeService);
    }

    @Test
    public void sendPDFWithMyGradesTest() throws Exception {
        doNothing().when(rabbitProducer).sendGrades(any());

        mvc.perform(post("/grades/pdf").header("Authorization", token))
                .andExpect(status().isOk());
        verify(rabbitProducer, times(1)).sendGrades(any());
        verifyNoMoreInteractions(rabbitProducer);
    }

    public ExerciseAndGrades getExampleGrades() {
        ArrayList<Integer> grades = new ArrayList<Integer>() {
            {
                add(3);
            }
        };

        HashMap<Exercises, ArrayList<Integer>> exerciseGradePair = new HashMap<Exercises, ArrayList<Integer>>() {
            {
                put(Exercises.CHEMY, grades);
            }
        };
        ExerciseAndGrades exerciseAndGrades = new ExerciseAndGrades(exerciseGradePair, "email@example.com");
        return exerciseAndGrades;
    }
}
