package com.application.service;

import com.application.exceptions.SchoolClassNotFound;
import com.application.tools.RandomStringGenerator;
import com.domain.AuthModel;
import com.entities.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

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
        String password = RandomStringGenerator.generate(16);
        switch (authModel.getRole()) {
            case "TEACHER" : {
                UserModel userModel = new UserModel(authModel.getEmail(), username, password, authModel.getRole());
                Teacher teacher = new Teacher(authModel.getFirstName(), authModel.getLastName(), authModel.getYearOfBirth());
                Exercise exercise = new Exercise(Exercises.valueOf(authModel.getExcercise()));

                userModel = userModelRepository.save(userModel);
                teacher = teacherRepository.save(teacher);
                exercise = exerciseRepository.save(exercise);

                userModel.setUserDetails(teacher);
                teacher.setUserModel(userModel);

                teacher.setExercise(exercise);
                exercise.setTeacher(teacher);

                userModelRepository.save(userModel);
                teacherRepository.save(teacher);
                exerciseRepository.save(exercise);

            }
            case "STUDENT": {
                SchoolClass schoolClass = schoolClassRepository.findByName(authModel.getSchoolClassName());
                if (!Optional.ofNullable(schoolClass).isPresent()) {
                    throw new SchoolClassNotFound("School class with that name doesn't exist!");
                }
                //nowy model
                //pobieramy mu przedmioty z klasy
                //przydzielamy klase
                //zapisuejemu userModelData
                //do bazy
            }
        }
        //sporzadzamy email

        emailService.sendSimpleEmail("puvac@send22u.info", "test", "content");
    }
}
