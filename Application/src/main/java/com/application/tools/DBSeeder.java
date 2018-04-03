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
        SchoolClass schoolClassGeography = new SchoolClass("3 A", Profile.GEOGRAPHY);
        SchoolClass schoolClassAng = new SchoolClass("2 A", Profile.ANGELIAN);
        SchoolClass schoolClassPhysic = new SchoolClass("1 B", Profile.PHYSICS);

        UserModel userModelTChem = new UserModel("teacherchem@grr.la","teacherchem","teacherpass", UserRole.TEACHER.name());
        UserModel userModelTMath = new UserModel("teachermath@grr.la","teachermath", "teacherpass",UserRole.TEACHER.name());
        UserModel userModelTGeo = new UserModel("teachergeo@grr.la","teachergeo", "teacherpass",UserRole.TEACHER.name());
        UserModel userModelTAng = new UserModel("teacherang@grr.la","teacherang", "teacherpass",UserRole.TEACHER.name());
        UserModel userModelTPhysic = new UserModel("teacherphysic@grr.la","teacherphysic", "teacherpass",UserRole.TEACHER.name());


        Teacher teacherChem = new Teacher("Adam","Żyła",
                getDateInFormat(1967, 2, 17));

        Teacher teacherMath = new Teacher("Janina","Kula",
                getDateInFormat(1973, 9, 1));
        Teacher teacherGeo = new Teacher("Karol","Swiat",
                getDateInFormat(1985, 1, 21));
        Teacher teacherAng = new Teacher("Lucyna","Wielka",
                getDateInFormat(1979, 3, 12));
        Teacher teacherPhysic = new Teacher("Danuta","Wektor",
                getDateInFormat(1970, 7, 17));

        Exercise exerciseChem = new Exercise(Exercises.CHEMY);

        Exercise exerciseMathOne = new Exercise(Exercises.MATH);
        Exercise exerciseMathTwo = new Exercise(Exercises.MATH);
        Exercise exerciseMathThree = new Exercise(Exercises.MATH);
        Exercise exerciseMathFour = new Exercise(Exercises.MATH);
        Exercise exerciseMathFive = new Exercise(Exercises.MATH);

        Exercise exerciseGeoOne = new Exercise(Exercises.GEOGRAPHY);
        Exercise exerciseGeoTwo = new Exercise(Exercises.GEOGRAPHY);
        Exercise exerciseGeoThree = new Exercise(Exercises.GEOGRAPHY);
        Exercise exerciseGeoFour = new Exercise(Exercises.GEOGRAPHY);
        Exercise exerciseGeoFive = new Exercise(Exercises.GEOGRAPHY);

        Exercise exerciseAngOne = new Exercise(Exercises.ENGLISH);
        Exercise exerciseAngTwo = new Exercise(Exercises.ENGLISH);
        Exercise exerciseAngThree = new Exercise(Exercises.ENGLISH);
        Exercise exerciseAngFour = new Exercise(Exercises.ENGLISH);
        Exercise exerciseAngFive = new Exercise(Exercises.ENGLISH);

        Exercise exercisePhysicOne = new Exercise(Exercises.PHYSICS);
        Exercise exercisePhysicTwo = new Exercise(Exercises.PHYSICS);
        Exercise exercisePhysicThree = new Exercise(Exercises.PHYSICS);
        Exercise exercisePhysicFour = new Exercise(Exercises.PHYSICS);
        Exercise exercisePhysicFive = new Exercise(Exercises.PHYSICS);

        //SAVING
        schoolClassChemBiol = schoolClassRepository.save(schoolClassChemBiol);
        schoolClassMath = schoolClassRepository.save(schoolClassMath);
        schoolClassGeography = schoolClassRepository.save(schoolClassGeography);
        schoolClassAng = schoolClassRepository.save(schoolClassAng);
        schoolClassPhysic = schoolClassRepository.save(schoolClassPhysic);

        userModelTChem = userModelRepository.save(userModelTChem);

        userModelTMath = userModelRepository.save(userModelTMath);
        userModelTGeo = userModelRepository.save(userModelTGeo);
        userModelTAng = userModelRepository.save(userModelTAng);
        userModelTPhysic = userModelRepository.save(userModelTPhysic);

        teacherChem = teacherRepository.save(teacherChem);
        teacherMath = teacherRepository.save(teacherMath);
        teacherGeo = teacherRepository.save(teacherGeo);
        teacherAng = teacherRepository.save(teacherAng);
        teacherPhysic = teacherRepository.save(teacherPhysic);

        exerciseChem = exerciseRepository.save(exerciseChem);

        exerciseMathOne = exerciseRepository.save(exerciseMathOne);
        exerciseMathTwo = exerciseRepository.save(exerciseMathTwo);
        exerciseMathThree = exerciseRepository.save(exerciseMathThree);
        exerciseMathFour = exerciseRepository.save(exerciseMathFour);
        exerciseMathFive = exerciseRepository.save(exerciseMathFive);

        exerciseGeoOne = exerciseRepository.save(exerciseGeoOne);
        exerciseGeoTwo = exerciseRepository.save(exerciseGeoTwo);
        exerciseGeoThree = exerciseRepository.save(exerciseGeoThree);
        exerciseGeoFour = exerciseRepository.save(exerciseGeoFour);
        exerciseGeoFive = exerciseRepository.save(exerciseGeoFive);

        exerciseAngOne = exerciseRepository.save(exerciseAngOne);
        exerciseAngTwo = exerciseRepository.save(exerciseAngTwo);
        exerciseAngThree = exerciseRepository.save(exerciseAngThree);
        exerciseAngFour = exerciseRepository.save(exerciseAngFour);
        exerciseAngFive = exerciseRepository.save(exerciseAngFive);

        exercisePhysicOne = exerciseRepository.save(exercisePhysicOne);
        exercisePhysicTwo = exerciseRepository.save(exercisePhysicTwo);
        exercisePhysicThree = exerciseRepository.save(exercisePhysicThree);
        exercisePhysicFour = exerciseRepository.save(exercisePhysicFour);
        exercisePhysicFive = exerciseRepository.save(exercisePhysicFive);

        //RELATIONS
        userModelTChem.setUserDetails(teacherChem);
        userModelTMath.setUserDetails(teacherMath);
        userModelTGeo.setUserDetails(teacherGeo);
        userModelTAng.setUserDetails(teacherAng);
        userModelTPhysic.setUserDetails(teacherPhysic);
        teacherChem.setUserModel(userModelTChem);
        teacherMath.setUserModel(userModelTMath);
        teacherGeo.setUserModel(userModelTGeo);
        teacherAng.setUserModel(userModelTAng);
        teacherPhysic.setUserModel(userModelTPhysic);

        teacherChem.getExercises().add(exerciseChem);
        exerciseChem.setTeacher(teacherChem);

        teacherMath.getExercises().add(exerciseMathOne);
        teacherMath.getExercises().add(exerciseMathTwo);
        teacherMath.getExercises().add(exerciseMathThree);
        teacherMath.getExercises().add(exerciseMathFour);
        teacherMath.getExercises().add(exerciseMathFive);
        exerciseMathOne.setTeacher(teacherMath);
        exerciseMathTwo.setTeacher(teacherMath);
        exerciseMathThree.setTeacher(teacherMath);
        exerciseMathFour.setTeacher(teacherMath);
        exerciseMathFive.setTeacher(teacherMath);

        teacherGeo.getExercises().add(exerciseGeoOne);
        teacherGeo.getExercises().add(exerciseGeoTwo);
        teacherGeo.getExercises().add(exerciseGeoThree);
        teacherGeo.getExercises().add(exerciseGeoFour);
        teacherGeo.getExercises().add(exerciseGeoFive);
        exerciseGeoOne.setTeacher(teacherGeo);
        exerciseGeoTwo.setTeacher(teacherGeo);
        exerciseGeoThree.setTeacher(teacherGeo);
        exerciseGeoFour.setTeacher(teacherGeo);
        exerciseGeoFive.setTeacher(teacherGeo);

        teacherAng.getExercises().add(exerciseAngOne);
        teacherAng.getExercises().add(exerciseAngTwo);
        teacherAng.getExercises().add(exerciseAngThree);
        teacherAng.getExercises().add(exerciseAngFour);
        teacherAng.getExercises().add(exerciseAngFive);
        exerciseAngOne.setTeacher(teacherAng);
        exerciseAngTwo.setTeacher(teacherAng);
        exerciseAngThree.setTeacher(teacherAng);
        exerciseAngFour.setTeacher(teacherAng);
        exerciseAngFive.setTeacher(teacherAng);

        teacherPhysic.getExercises().add(exercisePhysicOne);
        teacherPhysic.getExercises().add(exercisePhysicTwo);
        teacherPhysic.getExercises().add(exercisePhysicThree);
        teacherPhysic.getExercises().add(exercisePhysicFour);
        teacherPhysic.getExercises().add(exercisePhysicFive);
        exercisePhysicOne.setTeacher(teacherPhysic);
        exercisePhysicTwo.setTeacher(teacherPhysic);
        exercisePhysicThree.setTeacher(teacherPhysic);
        exercisePhysicFour.setTeacher(teacherPhysic);
        exercisePhysicFive.setTeacher(teacherPhysic);

        teacherChem.setSchoolClass(schoolClassChemBiol);
        teacherMath.setSchoolClass(schoolClassMath);
        teacherGeo.setSchoolClass(schoolClassGeography);
        teacherAng.setSchoolClass(schoolClassAng);
        teacherPhysic.setSchoolClass(schoolClassPhysic);
        schoolClassChemBiol.setTutor(teacherChem);
        schoolClassMath.setTutor(teacherMath);
        schoolClassGeography.setTutor(teacherGeo);
        schoolClassAng.setTutor(teacherAng);
        schoolClassPhysic.setTutor(teacherPhysic);


        exerciseChem.setSchoolClass(schoolClassChemBiol);
        schoolClassChemBiol.getExercises().add(exerciseChem);

        exerciseMathOne.setSchoolClass(schoolClassMath);
        exerciseMathTwo.setSchoolClass(schoolClassChemBiol);
        exerciseMathThree.setSchoolClass(schoolClassGeography);
        exerciseMathFour.setSchoolClass(schoolClassAng);
        exerciseMathFive.setSchoolClass(schoolClassPhysic);
        schoolClassChemBiol.getExercises().add(exerciseMathTwo);
        schoolClassMath.getExercises().add(exerciseMathOne);
        schoolClassGeography.getExercises().add(exerciseMathThree);
        schoolClassAng.getExercises().add(exerciseMathFour);
        schoolClassPhysic.getExercises().add(exerciseMathFive);

        exerciseGeoOne.setSchoolClass(schoolClassMath);
        exerciseGeoTwo.setSchoolClass(schoolClassChemBiol);
        exerciseGeoThree.setSchoolClass(schoolClassGeography);
        exerciseGeoFour.setSchoolClass(schoolClassAng);
        exerciseGeoFive.setSchoolClass(schoolClassPhysic);
        schoolClassChemBiol.getExercises().add(exerciseGeoTwo);
        schoolClassMath.getExercises().add(exerciseGeoOne);
        schoolClassGeography.getExercises().add(exerciseGeoThree);
        schoolClassAng.getExercises().add(exerciseGeoFour);
        schoolClassPhysic.getExercises().add(exerciseGeoFive);

        exerciseAngOne.setSchoolClass(schoolClassMath);
        exerciseAngTwo.setSchoolClass(schoolClassChemBiol);
        exerciseAngThree.setSchoolClass(schoolClassGeography);
        exerciseAngFour.setSchoolClass(schoolClassAng);
        exerciseAngFive.setSchoolClass(schoolClassPhysic);
        schoolClassChemBiol.getExercises().add(exerciseAngTwo);
        schoolClassMath.getExercises().add(exerciseAngOne);
        schoolClassGeography.getExercises().add(exerciseAngThree);
        schoolClassAng.getExercises().add(exerciseAngFour);
        schoolClassPhysic.getExercises().add(exerciseAngFive);

        exercisePhysicOne.setSchoolClass(schoolClassMath);
        exercisePhysicTwo.setSchoolClass(schoolClassChemBiol);
        exercisePhysicThree.setSchoolClass(schoolClassGeography);
        exercisePhysicFour.setSchoolClass(schoolClassAng);
        exercisePhysicFive.setSchoolClass(schoolClassPhysic);
        schoolClassChemBiol.getExercises().add(exercisePhysicTwo);
        schoolClassMath.getExercises().add(exercisePhysicOne);
        schoolClassGeography.getExercises().add(exercisePhysicThree);
        schoolClassAng.getExercises().add(exercisePhysicFour);
        schoolClassPhysic.getExercises().add(exercisePhysicFive);

        schoolClassChemBiol = schoolClassRepository.save(schoolClassChemBiol);
        schoolClassMath = schoolClassRepository.save(schoolClassMath);
        schoolClassGeography = schoolClassRepository.save(schoolClassGeography);
        schoolClassAng = schoolClassRepository.save(schoolClassAng);
        schoolClassPhysic = schoolClassRepository.save(schoolClassPhysic);

        userModelRepository.save(userModelTChem);
        userModelRepository.save(userModelTMath);
        userModelRepository.save(userModelTGeo);
        userModelRepository.save(userModelTAng);
        userModelRepository.save(userModelTPhysic);

        exerciseChem = exerciseRepository.save(exerciseChem);

        exerciseMathOne = exerciseRepository.save(exerciseMathOne);
        exerciseMathTwo = exerciseRepository.save(exerciseMathTwo);
        exerciseMathThree = exerciseRepository.save(exerciseMathThree);
        exerciseMathFour = exerciseRepository.save(exerciseMathFour);
        exerciseMathFive = exerciseRepository.save(exerciseMathFive);

        exerciseGeoOne = exerciseRepository.save(exerciseGeoOne);
        exerciseGeoTwo = exerciseRepository.save(exerciseGeoTwo);
        exerciseGeoThree = exerciseRepository.save(exerciseGeoThree);
        exerciseGeoFour = exerciseRepository.save(exerciseGeoFour);
        exerciseGeoFive = exerciseRepository.save(exerciseGeoFive);

        exerciseAngOne = exerciseRepository.save(exerciseAngOne);
        exerciseAngTwo = exerciseRepository.save(exerciseAngTwo);
        exerciseAngThree = exerciseRepository.save(exerciseAngThree);
        exerciseAngFour = exerciseRepository.save(exerciseAngFour);
        exerciseAngFive = exerciseRepository.save(exerciseAngFive);

        exercisePhysicOne = exerciseRepository.save(exercisePhysicOne);
        exercisePhysicTwo = exerciseRepository.save(exercisePhysicTwo);
        exercisePhysicThree = exerciseRepository.save(exercisePhysicThree);
        exercisePhysicFour = exerciseRepository.save(exercisePhysicFour);
        exercisePhysicFive = exerciseRepository.save(exercisePhysicFive);

        teacherRepository.save(teacherChem);
        teacherRepository.save(teacherMath);
        teacherRepository.save(teacherGeo);
        teacherRepository.save(teacherAng);
        teacherRepository.save(teacherPhysic);

        //CREATING STUDENT TO CLASS ONE
        UserModel studentOneUserModel = new UserModel(
                "karol98@grr.la", "karol98", "studentone",UserRole.STUDENT.name());
        UserModel studentTwoUserModel = new UserModel(
                "sabina98@grr.la", "sabina98", "studenttwo",UserRole.STUDENT.name());
        UserModel studentThreeUserModel = new UserModel(
                "filip98@grr.la", "filip98", "studentthree",UserRole.STUDENT.name());


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
                "adam97@grr.la", "adam97", "studentfour",UserRole.STUDENT.name());
        UserModel studentFiveUserModel = new UserModel(
                "emilia97@grr.la", "emilia97", "studentfive",UserRole.STUDENT.name());
        UserModel studentSixUserModel = new UserModel(
                "krystian97@grr.la", "krystian97", "studentfive",UserRole.STUDENT.name());


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
