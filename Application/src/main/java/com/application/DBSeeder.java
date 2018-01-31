package com.application;

import com.entities.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DBSeeder {
    @Autowired
    private SchoolClassRepository schoolClassRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private UserModelRepository userModelRepository;
    @Autowired
    private GradeRepository gradeRepository;

    @PostConstruct
    public void seed() throws ParseException {
        SchoolClass schoolClassOne = new SchoolClass("1 A", Profile.CHEMISTRY_AND_BIOLOGY);
        SchoolClass schoolClassTwo = new SchoolClass("2 B", Profile.MATHEMATICS);

        UserModel userModelOne = new UserModel("teacherone@mail.com","teacherone","teacherpass", UserRole.TEACHER.name());
        UserModel userModelTwo = new UserModel("teachertwo@mail.com","teachertwo", "teacherpass",UserRole.TEACHER.name());


        Teacher teacherOne = new Teacher("Adam","Żyła",
                getDateInFormat(1967, 2, 17));
        Teacher teacherTwo = new Teacher("Janina","Kula",
                getDateInFormat(1973, 9, 1));

        Exercise exerciseOne = new Exercise(Exercises.CHEMY);
        Exercise exerciseTwo = new Exercise(Exercises.MATH);

        //SAVING
        schoolClassOne = schoolClassRepository.save(schoolClassOne);
        schoolClassTwo = schoolClassRepository.save(schoolClassTwo);

        userModelOne = userModelRepository.save(userModelOne);
        userModelTwo = userModelRepository.save(userModelTwo);

        teacherOne = teacherRepository.save(teacherOne);
        teacherTwo = teacherRepository.save(teacherTwo);

        exerciseOne = exerciseRepository.save(exerciseOne);
        exerciseTwo = exerciseRepository.save(exerciseTwo);

        //RELATIONS
        userModelOne.setUserDetails(teacherOne);
        userModelTwo.setUserDetails(teacherTwo);
        teacherOne.setUserModel(userModelOne);
        teacherTwo.setUserModel(userModelTwo);

        teacherOne.setExercise(exerciseOne);
        teacherTwo.setExercise(exerciseTwo);
        exerciseOne.setTeacher(teacherOne);
        exerciseTwo.setTeacher(teacherTwo);

        teacherOne.setSchoolClass(schoolClassOne);
        teacherTwo.setSchoolClass(schoolClassTwo);
        schoolClassOne.setTutor(teacherOne);
        schoolClassTwo.setTutor(teacherTwo);

        exerciseOne.setSchoolClass(schoolClassOne);
        exerciseTwo.setSchoolClass(schoolClassTwo);
        schoolClassOne.getExercises().add(exerciseOne);
        schoolClassTwo.getExercises().add(exerciseTwo);

        schoolClassOne = schoolClassRepository.save(schoolClassOne);
        schoolClassTwo = schoolClassRepository.save(schoolClassTwo);

        userModelRepository.save(userModelOne);
        userModelRepository.save(userModelTwo);

        exerciseOne = exerciseRepository.save(exerciseOne);
        exerciseTwo = exerciseRepository.save(exerciseTwo);

        teacherOne = teacherRepository.save(teacherOne);
        teacherTwo = teacherRepository.save(teacherTwo);

        //CREATING STUDENT TO CLASS ONE
        UserModel studentOneUserModel = new UserModel(
                "karol98@mail.com", "karol98", "studentone",UserRole.STUDENT.name());
        UserModel studentTwoUserModel = new UserModel(
                "sabina98@mail.com", "sabina98", "studenttwo",UserRole.STUDENT.name());
        UserModel studentThreeUserModel = new UserModel(
                "filip98@mail.com", "filip98", "studentthree",UserRole.STUDENT.name());


        Student studentOne = new Student("Karol","Dynia",getDateInFormat(1998, 6,21));
        Student studentTwo = new Student("Sabina","Krakowska",getDateInFormat(1998, 2,7));
        Student studentThree = new Student("Filip","Tomkow",getDateInFormat(1998, 1,15));

        Grade gradeOne = new Grade(getDateInFormat(2018, 1,21), 2, "Sprawdzian");
        Grade gradeTwo = new Grade(getDateInFormat(2018, 1,21), 4, "Sprawdzian");
        Grade gradeThree = new Grade(getDateInFormat(2018, 1,21), 3, "Sprawdzian");

        //SAVING
        studentOneUserModel = userModelRepository.save(studentOneUserModel);
        studentTwoUserModel = userModelRepository.save(studentTwoUserModel);
        studentThreeUserModel = userModelRepository.save(studentThreeUserModel);

        studentOne = studentRepository.save(studentOne);
        studentTwo = studentRepository.save(studentTwo);
        studentThree = studentRepository.save(studentThree);

        gradeOne = gradeRepository.save(gradeOne);
        gradeTwo = gradeRepository.save(gradeTwo);
        gradeThree = gradeRepository.save(gradeThree);

        //RELATIONS
        studentOneUserModel.setUserDetails(studentOne);
        studentOne.setUserModel(studentOneUserModel);
        studentTwoUserModel.setUserDetails(studentTwo);
        studentTwo.setUserModel(studentTwoUserModel);
        studentThreeUserModel.setUserDetails(studentThree);
        studentThree.setUserModel(studentThreeUserModel);



    }

    public Date getDateInFormat(int year, int month, int day) throws ParseException {
        String dateToParse = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateToParse);
    }
}
