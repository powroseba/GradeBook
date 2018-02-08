package com.application.tools;

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

//    @PostConstruct
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

        teacherOne.getExercises().add(exerciseOne);
        teacherTwo.getExercises().add(exerciseTwo);
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

        teacherRepository.save(teacherOne);
        teacherRepository.save(teacherTwo);

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

        studentOne.setSchoolClass(schoolClassOne);
        schoolClassOne.getStudents().add(studentOne);
        studentTwo.setSchoolClass(schoolClassOne);
        schoolClassOne.getStudents().add(studentTwo);
        studentThree.setSchoolClass(schoolClassOne);
        schoolClassOne.getStudents().add(studentThree);

        studentOne.getExercises().add(exerciseOne);
        exerciseOne.getStudents().add(studentOne);
        studentTwo.getExercises().add(exerciseOne);
        exerciseOne.getStudents().add(studentTwo);
        studentThree.getExercises().add(exerciseOne);
        exerciseOne.getStudents().add(studentThree);

        studentOne.getGrades().add(gradeOne);
        gradeOne.setStudent(studentOne);
        studentTwo.getGrades().add(gradeTwo);
        gradeTwo.setStudent(studentTwo);
        studentThree.getGrades().add(gradeThree);
        gradeThree.setStudent(studentThree);

        gradeOne.setExercise(exerciseOne);
        exerciseOne.getGrades().add(gradeOne);
        gradeTwo.setExercise(exerciseOne);
        exerciseOne.getGrades().add(gradeTwo);
        gradeThree.setExercise(exerciseOne);
        exerciseOne.getGrades().add(gradeThree);

        userModelRepository.save(studentOneUserModel);
        userModelRepository.save(studentTwoUserModel);
        userModelRepository.save(studentThreeUserModel);

        studentRepository.save(studentOne);
        studentRepository.save(studentTwo);
        studentRepository.save(studentThree);

        gradeRepository.save(gradeOne);
        gradeRepository.save(gradeTwo);
        gradeRepository.save(gradeThree);

        exerciseRepository.save(exerciseOne);

        //CREATING STUDENT TO CLASS ONE
        UserModel studentFourUserModel = new UserModel(
                "adam97@mail.com", "adam97", "studentfour",UserRole.STUDENT.name());
        UserModel studentFiveUserModel = new UserModel(
                "emilia97@mail.com", "emilia97", "studentfive",UserRole.STUDENT.name());
        UserModel studentSixUserModel = new UserModel(
                "krystian97@mail.com", "krystian97", "studentfive",UserRole.STUDENT.name());


        Student studentFour = new Student("Adam","Kucharz",getDateInFormat(1997, 1,2));
        Student studentFive = new Student("Emilia","Pliszka",getDateInFormat(1997, 2,3));
        Student studentSix = new Student("Radek","Szafa",getDateInFormat(1997, 1,23));

        Grade gradeFour = new Grade(getDateInFormat(2018, 1,3), 3, "Sprawdzian");
        Grade gradeFive = new Grade(getDateInFormat(2018, 1,3), 5, "Sprawdzian");
        Grade gradeSix = new Grade(getDateInFormat(2018, 1,3), 5, "Sprawdzian");

        //SAVING
        studentFourUserModel = userModelRepository.save(studentFourUserModel);
        studentFiveUserModel = userModelRepository.save(studentFiveUserModel);
        studentSixUserModel = userModelRepository.save(studentSixUserModel);

        studentFour = studentRepository.save(studentFour);
        studentFive = studentRepository.save(studentFive);
        studentSix = studentRepository.save(studentSix);

        gradeFour = gradeRepository.save(gradeFour);
        gradeFive = gradeRepository.save(gradeFive);
        gradeSix = gradeRepository.save(gradeSix);

        //RELATIONS
        studentFourUserModel.setUserDetails(studentFour);
        studentFour.setUserModel(studentFourUserModel);
        studentFiveUserModel.setUserDetails(studentFive);
        studentFive.setUserModel(studentFiveUserModel);
        studentSixUserModel.setUserDetails(studentSix);
        studentSix.setUserModel(studentSixUserModel);

        studentFour.setSchoolClass(schoolClassTwo);
        schoolClassTwo.getStudents().add(studentFour);
        studentFive.setSchoolClass(schoolClassTwo);
        schoolClassTwo.getStudents().add(studentFive);
        studentSix.setSchoolClass(schoolClassTwo);
        schoolClassTwo.getStudents().add(studentSix);

        studentFour.getExercises().add(exerciseTwo);
        exerciseTwo.getStudents().add(studentFour);
        studentFive.getExercises().add(exerciseTwo);
        exerciseTwo.getStudents().add(studentFive);
        studentSix.getExercises().add(exerciseTwo);
        exerciseTwo.getStudents().add(studentSix);

        studentFour.getGrades().add(gradeFour);
        gradeFour.setStudent(studentFour);
        studentFive.getGrades().add(gradeFive);
        gradeFive.setStudent(studentFive);
        studentSix.getGrades().add(gradeSix);
        gradeSix.setStudent(studentSix);

        gradeFour.setExercise(exerciseTwo);
        exerciseTwo.getGrades().add(gradeFour);
        gradeFive.setExercise(exerciseTwo);
        exerciseTwo.getGrades().add(gradeFive);
        gradeSix.setExercise(exerciseTwo);
        exerciseTwo.getGrades().add(gradeSix);

        userModelRepository.save(studentFourUserModel);
        userModelRepository.save(studentFiveUserModel);
        userModelRepository.save(studentSixUserModel);

        studentRepository.save(studentFour);
        studentRepository.save(studentFive);
        studentRepository.save(studentSix);

        gradeRepository.save(gradeFour);
        gradeRepository.save(gradeFive);
        gradeRepository.save(gradeSix);

        exerciseRepository.save(exerciseTwo);
        UserModel userModel = new UserModel("admin@mail.com", "admin","adminpass",UserRole.ADMIN.name());
        userModelRepository.save(userModel);
    }

    public Date getDateInFormat(int year, int month, int day) throws ParseException {
        String dateToParse = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateToParse);
    }
}
