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
        SchoolClass schoolClassChemBiol = new SchoolClass("1 A", Profile.CHEMISTRY_AND_BIOLOGY);
        SchoolClass schoolClassMath = new SchoolClass("2 B", Profile.MATHEMATICS);

        UserModel userModelOne = new UserModel("teacherone@mail.com","teacherone","teacherpass", UserRole.TEACHER.name());
        UserModel userModelTwo = new UserModel("teachertwo@mail.com","teachertwo", "teacherpass",UserRole.TEACHER.name());


        Teacher teacherChem = new Teacher("Adam","Żyła",
                getDateInFormat(1967, 2, 17));
        Teacher teacherMath = new Teacher("Janina","Kula",
                getDateInFormat(1973, 9, 1));

        Exercise exerciseChem = new Exercise(Exercises.CHEMY);
        Exercise exerciseMathOne = new Exercise(Exercises.MATH);
        Exercise exerciseMathTwo = new Exercise(Exercises.MATH);

        //SAVING
        schoolClassChemBiol = schoolClassRepository.save(schoolClassChemBiol);
        schoolClassMath = schoolClassRepository.save(schoolClassMath);

        userModelOne = userModelRepository.save(userModelOne);
        userModelTwo = userModelRepository.save(userModelTwo);

        teacherChem = teacherRepository.save(teacherChem);
        teacherMath = teacherRepository.save(teacherMath);

        exerciseChem = exerciseRepository.save(exerciseChem);
        exerciseMathOne = exerciseRepository.save(exerciseMathOne);
        exerciseMathTwo = exerciseRepository.save(exerciseMathTwo);

        //RELATIONS
        userModelOne.setUserDetails(teacherChem);
        userModelTwo.setUserDetails(teacherMath);
        teacherChem.setUserModel(userModelOne);
        teacherMath.setUserModel(userModelTwo);

        teacherChem.getExercises().add(exerciseChem);
        teacherMath.getExercises().add(exerciseMathOne);
        teacherMath.getExercises().add(exerciseMathTwo);
        exerciseChem.setTeacher(teacherChem);
        exerciseMathOne.setTeacher(teacherMath);
        exerciseMathTwo.setTeacher(teacherMath);

        teacherChem.setSchoolClass(schoolClassChemBiol);
        teacherMath.setSchoolClass(schoolClassMath);
        schoolClassChemBiol.setTutor(teacherChem);
        schoolClassMath.setTutor(teacherMath);

        exerciseChem.setSchoolClass(schoolClassChemBiol);
        exerciseMathTwo.setSchoolClass(schoolClassChemBiol);
        exerciseMathOne.setSchoolClass(schoolClassMath);
        schoolClassChemBiol.getExercises().add(exerciseChem);
        schoolClassChemBiol.getExercises().add(exerciseMathTwo);
        schoolClassMath.getExercises().add(exerciseMathOne);

        schoolClassChemBiol = schoolClassRepository.save(schoolClassChemBiol);
        schoolClassMath = schoolClassRepository.save(schoolClassMath);

        userModelRepository.save(userModelOne);
        userModelRepository.save(userModelTwo);

        exerciseChem = exerciseRepository.save(exerciseChem);
        exerciseMathOne = exerciseRepository.save(exerciseMathOne);
        exerciseMathTwo = exerciseRepository.save(exerciseMathTwo);

        teacherRepository.save(teacherChem);
        teacherRepository.save(teacherMath);

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

        Grade gradeMathOne = new Grade(getDateInFormat(2018, 1,30), 2, "Kartkowka");
        Grade gradeMathTwo = new Grade(getDateInFormat(2018, 1,30), 4, "Kartkowka");
        Grade gradeMathThree = new Grade(getDateInFormat(2018, 1,30), 3, "Kartkowka");

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

        gradeMathOne = gradeRepository.save(gradeMathOne);
        gradeMathTwo = gradeRepository.save(gradeMathTwo);
        gradeMathThree = gradeRepository.save(gradeMathThree);

        //RELATIONS
        studentOneUserModel.setUserDetails(studentOne);
        studentOne.setUserModel(studentOneUserModel);
        studentTwoUserModel.setUserDetails(studentTwo);
        studentTwo.setUserModel(studentTwoUserModel);
        studentThreeUserModel.setUserDetails(studentThree);
        studentThree.setUserModel(studentThreeUserModel);

        studentOne.setSchoolClass(schoolClassChemBiol);
        schoolClassChemBiol.getStudents().add(studentOne);
        studentTwo.setSchoolClass(schoolClassChemBiol);
        schoolClassChemBiol.getStudents().add(studentTwo);
        studentThree.setSchoolClass(schoolClassChemBiol);
        schoolClassChemBiol.getStudents().add(studentThree);

        studentOne.getExercises().add(exerciseChem);
        studentOne.getExercises().add(exerciseMathTwo);
        exerciseChem.getStudents().add(studentOne);
        exerciseMathTwo.getStudents().add(studentOne);

        studentTwo.getExercises().add(exerciseChem);
        studentTwo.getExercises().add(exerciseMathTwo);
        exerciseChem.getStudents().add(studentTwo);
        exerciseMathTwo.getStudents().add(studentTwo);

        studentThree.getExercises().add(exerciseChem);
        studentThree.getExercises().add(exerciseMathTwo);
        exerciseChem.getStudents().add(studentThree);
        exerciseMathTwo.getStudents().add(studentThree);

        studentOne.getGrades().add(gradeOne);
        studentOne.getGrades().add(gradeMathOne);
        gradeOne.setStudent(studentOne);
        gradeMathOne.setStudent(studentOne);

        studentTwo.getGrades().add(gradeTwo);
        studentTwo.getGrades().add(gradeMathTwo);
        gradeTwo.setStudent(studentTwo);
        gradeMathTwo.setStudent(studentTwo);

        studentThree.getGrades().add(gradeThree);
        studentThree.getGrades().add(gradeMathThree);
        gradeThree.setStudent(studentThree);
        gradeMathThree.setStudent(studentThree);

        gradeOne.setExercise(exerciseChem);
        gradeMathOne.setExercise(exerciseMathTwo);
        exerciseChem.getGrades().add(gradeOne);
        exerciseMathTwo.getGrades().add(gradeMathOne);

        gradeTwo.setExercise(exerciseChem);
        gradeMathTwo.setExercise(exerciseMathTwo);
        exerciseChem.getGrades().add(gradeTwo);
        exerciseMathTwo.getGrades().add(gradeMathTwo);

        gradeThree.setExercise(exerciseChem);
        gradeMathThree.setExercise(exerciseMathTwo);
        exerciseChem.getGrades().add(gradeThree);
        exerciseMathTwo.getGrades().add(gradeMathThree);

        userModelRepository.save(studentOneUserModel);
        userModelRepository.save(studentTwoUserModel);
        userModelRepository.save(studentThreeUserModel);

        studentRepository.save(studentOne);
        studentRepository.save(studentTwo);
        studentRepository.save(studentThree);

        gradeRepository.save(gradeOne);
        gradeRepository.save(gradeTwo);
        gradeRepository.save(gradeThree);
        gradeRepository.save(gradeMathOne);
        gradeRepository.save(gradeMathTwo);
        gradeRepository.save(gradeMathThree);

        exerciseRepository.save(exerciseChem);
        exerciseRepository.save(exerciseMathTwo);

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

        studentFour.setSchoolClass(schoolClassMath);
        schoolClassMath.getStudents().add(studentFour);
        studentFive.setSchoolClass(schoolClassMath);
        schoolClassMath.getStudents().add(studentFive);
        studentSix.setSchoolClass(schoolClassMath);
        schoolClassMath.getStudents().add(studentSix);

        studentFour.getExercises().add(exerciseMathOne);
        exerciseMathOne.getStudents().add(studentFour);
        studentFive.getExercises().add(exerciseMathOne);
        exerciseMathOne.getStudents().add(studentFive);
        studentSix.getExercises().add(exerciseMathOne);
        exerciseMathOne.getStudents().add(studentSix);

        studentFour.getGrades().add(gradeFour);
        gradeFour.setStudent(studentFour);
        studentFive.getGrades().add(gradeFive);
        gradeFive.setStudent(studentFive);
        studentSix.getGrades().add(gradeSix);
        gradeSix.setStudent(studentSix);

        gradeFour.setExercise(exerciseMathOne);
        exerciseMathOne.getGrades().add(gradeFour);
        gradeFive.setExercise(exerciseMathOne);
        exerciseMathOne.getGrades().add(gradeFive);
        gradeSix.setExercise(exerciseMathOne);
        exerciseMathOne.getGrades().add(gradeSix);

        userModelRepository.save(studentFourUserModel);
        userModelRepository.save(studentFiveUserModel);
        userModelRepository.save(studentSixUserModel);

        studentRepository.save(studentFour);
        studentRepository.save(studentFive);
        studentRepository.save(studentSix);

        gradeRepository.save(gradeFour);
        gradeRepository.save(gradeFive);
        gradeRepository.save(gradeSix);

        exerciseRepository.save(exerciseMathOne);
        UserModel userModel = new UserModel("admin@mail.com", "admin","adminpass",UserRole.ADMIN.name());
        userModelRepository.save(userModel);
    }

    public Date getDateInFormat(int year, int month, int day) throws ParseException {
        String dateToParse = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateToParse);
    }
}
