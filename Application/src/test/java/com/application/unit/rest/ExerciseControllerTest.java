package com.application.unit.rest;

import com.application.Application;
import com.application.ConvertJavaObjectToJson;
import com.application.TokenGenerator;
import com.application.exceptions.notfound.ExerciseNotFoundException;
import com.application.exceptions.notfound.TeacherNotFoundException;
import com.application.service.ExerciseService;
import com.domain.dto.ExerciseDTO;
import com.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class ExerciseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExerciseService exerciseService;

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private String token = "";

    @Before
    public void generateToken() {
        this.token = tokenGenerator.generate(10, "username", UserRole.TEACHER);
    }

    @Test
    public void getTeacherExercisesTest() throws Exception {
        ExerciseDTO exercise = getExampleExercise().get(0);
        when(exerciseService.getTeacherExercises(any())).thenReturn(getExampleExercise());

        mvc.perform(get("/exercises/teacher").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].nameOfExercise", is(exercise.getNameOfExercise().toString())))
                .andExpect(jsonPath("$[0].exerciseId", is(1)))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(exerciseService, times(1)).getTeacherExercises(any());
        verifyNoMoreInteractions(exerciseService);
    }

    @Test
    public void getTeacherExercisesExpectTeacherNotFound() throws Exception {
        when(exerciseService.getTeacherExercises(any())).thenThrow(new TeacherNotFoundException());

        mvc.perform(get("/exercises/teacher").header("Authorization", token))
                .andExpect(status().isNotFound());
        verify(exerciseService, times(1)).getTeacherExercises(any());
        verifyNoMoreInteractions(exerciseService);
    }

    @Test
    public void getTeacherExercisesExpectExerciseNotFound() throws Exception {
        when(exerciseService.getTeacherExercises(any())).thenThrow(new ExerciseNotFoundException());

        mvc.perform(get("/exercises/teacher").header("Authorization", token))
                .andExpect(status().isNotFound());
        verify(exerciseService, times(1)).getTeacherExercises(any());
        verifyNoMoreInteractions(exerciseService);
    }

    @Test
    public void getTeacherExercisesExpectAccessDenied() throws Exception {
        token = tokenGenerator.generate(10, "username", UserRole.STUDENT);
        mvc.perform(get("/exercises/teacher").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    public List<ExerciseDTO> getExampleExercise() {
       return new ArrayList<ExerciseDTO>() {
            {
                add(new ExerciseDTO(1L,new Teacher(), Exercises.BIOLOGY, new HashSet<Grade>(),
                        new SchoolClass(), new HashSet<Student>()));
                add(new ExerciseDTO(2L,new Teacher(), Exercises.BIOLOGY, new HashSet<Grade>(),
                        new SchoolClass(), new HashSet<Student>()));
            }
        };
    }
}
