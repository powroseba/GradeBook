package com.application.tools;

import com.entities.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

        UserModel userModelTChem = new UserModel("teacherchem@grr.la", "teacherchem", "teacherpass", UserRole.TEACHER.name());
        UserModel userModelTMath = new UserModel("teachermath@grr.la", "teachermath", "teacherpass", UserRole.TEACHER.name());
        UserModel userModelTGeo = new UserModel("teachergeo@grr.la", "teachergeo", "teacherpass", UserRole.TEACHER.name());
        UserModel userModelTAng = new UserModel("teacherang@grr.la", "teacherang", "teacherpass", UserRole.TEACHER.name());
        UserModel userModelTPhysic = new UserModel("teacherphysic@grr.la", "teacherphysic", "teacherpass", UserRole.TEACHER.name());


        Teacher teacherChem = new Teacher("Adam", "Żyła",
                getDateInFormat(1967, 2, 17));
        Teacher teacherMath = new Teacher("Janina", "Kula",
                getDateInFormat(1973, 9, 1));
        Teacher teacherGeo = new Teacher("Karol", "Swiat",
                getDateInFormat(1985, 1, 21));
        Teacher teacherAng = new Teacher("Lucyna", "Wielka",
                getDateInFormat(1979, 3, 12));
        Teacher teacherPhysic = new Teacher("Danuta", "Wektor",
                getDateInFormat(1970, 7, 17));

        Exercise exerciseChemOne = new Exercise(Exercises.CHEMY);
        Exercise exerciseChemTwo = new Exercise(Exercises.CHEMY);
        Exercise exerciseChemThree = new Exercise(Exercises.CHEMY);
        Exercise exerciseChemFour = new Exercise(Exercises.CHEMY);
        Exercise exerciseChemFive = new Exercise(Exercises.CHEMY);

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

        exerciseChemOne = exerciseRepository.save(exerciseChemOne);
        exerciseChemTwo = exerciseRepository.save(exerciseChemTwo);
        exerciseChemThree = exerciseRepository.save(exerciseChemThree);
        exerciseChemFour = exerciseRepository.save(exerciseChemFour);
        exerciseChemFive = exerciseRepository.save(exerciseChemFive);

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

        teacherChem.getExercises().add(exerciseChemOne);
        teacherChem.getExercises().add(exerciseChemTwo);
        teacherChem.getExercises().add(exerciseChemThree);
        teacherChem.getExercises().add(exerciseChemFour);
        teacherChem.getExercises().add(exerciseChemFive);
        exerciseChemOne.setTeacher(teacherChem);
        exerciseChemTwo.setTeacher(teacherChem);
        exerciseChemThree.setTeacher(teacherChem);
        exerciseChemFour.setTeacher(teacherChem);
        exerciseChemFive.setTeacher(teacherChem);

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


        exerciseChemOne.setSchoolClass(schoolClassChemBiol);
        exerciseChemTwo.setSchoolClass(schoolClassChemBiol);
        exerciseChemThree.setSchoolClass(schoolClassChemBiol);
        exerciseChemFour.setSchoolClass(schoolClassChemBiol);
        exerciseChemFive.setSchoolClass(schoolClassChemBiol);
        schoolClassChemBiol.getExercises().add(exerciseChemOne);
        schoolClassChemBiol.getExercises().add(exerciseChemTwo);
        schoolClassChemBiol.getExercises().add(exerciseChemThree);
        schoolClassChemBiol.getExercises().add(exerciseChemFour);
        schoolClassChemBiol.getExercises().add(exerciseChemFive);

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

        exerciseChemOne = exerciseRepository.save(exerciseChemOne);
        exerciseChemTwo = exerciseRepository.save(exerciseChemTwo);
        exerciseChemThree = exerciseRepository.save(exerciseChemThree);
        exerciseChemFour = exerciseRepository.save(exerciseChemFour);
        exerciseChemFive = exerciseRepository.save(exerciseChemFive);

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

        List<SchoolClass> schoolClassList = Arrays.asList(
                schoolClassChemBiol, schoolClassMath, schoolClassGeography, schoolClassAng, schoolClassPhysic
        );

        List<Exercise> chemyList = Arrays.asList(
                exerciseChemOne, exerciseChemTwo, exerciseChemThree, exerciseChemFour, exerciseChemFive
        );

        List<Exercise> mathList = Arrays.asList(
                exerciseMathOne, exerciseMathTwo, exerciseMathThree, exerciseMathFour, exerciseMathFive
        );

        List<Exercise> geographicList = Arrays.asList(
                exerciseGeoOne, exerciseGeoTwo, exerciseGeoThree, exerciseGeoFour, exerciseGeoFive
        );

        List<Exercise> englishList = Arrays.asList(
                exerciseAngOne, exerciseAngTwo, exerciseAngThree, exerciseAngFour, exerciseAngFive
        );

        List<Exercise> physicsList = Arrays.asList(
                exercisePhysicOne, exercisePhysicTwo, exercisePhysicThree, exercisePhysicFour, exercisePhysicFive
        );


        for (int i = 0; i < schoolClassList.size(); i++) {
            SchoolClass schoolClass = schoolClassList.get(i);
            String className = schoolClass.getName().replace(" ", "");

            UserModel student1UserModel = userModelRepository.save(new UserModel(
                    "karol" + className + "@grr.la", "karol" + className, "student", UserRole.STUDENT.name()));
            UserModel student2UserModel = userModelRepository.save(new UserModel(
                    "sabina" + className + "@grr.la", "sabina" + className, "student", UserRole.STUDENT.name()));
            UserModel student3UserModel = userModelRepository.save(new UserModel(
                    "filip" + className + "@grr.la", "filip" + className, "student", UserRole.STUDENT.name()));
            UserModel student4UserModel = userModelRepository.save(new UserModel(
                    "michal" + className + "@grr.la", "michal" + className, "student", UserRole.STUDENT.name()));
            UserModel student5UserModel = userModelRepository.save(new UserModel(
                    "emilia" + className + "@grr.la", "emilia" + className, "student", UserRole.STUDENT.name()));
            UserModel student6UserModel = userModelRepository.save(new UserModel(
                    "hubert" + className + "@grr.la", "hubert" + className, "student", UserRole.STUDENT.name()));
            UserModel student7UserModel = userModelRepository.save(new UserModel(
                    "lukasz" + className + "@grr.la", "lukasz" + className, "student", UserRole.STUDENT.name()));
            UserModel student8UserModel = userModelRepository.save(new UserModel(
                    "karolina" + className + "@grr.la", "karolina" + className, "student", UserRole.STUDENT.name()));
            UserModel student9UserModel = userModelRepository.save(new UserModel(
                    "sebastian" + className + "@grr.la", "sebastian" + className, "student", UserRole.STUDENT.name()));
            UserModel student10UserModel = userModelRepository.save(new UserModel(
                    "dominika" + className + "@grr.la", "dominika" + className, "student", UserRole.STUDENT.name()));


            Student student1 = studentRepository.save(new Student("Karol", "Dynia", getDateInFormat(1999 - i, 6, 21)));
            Student student2 = studentRepository.save(new Student("Sabina", "Krakowska", getDateInFormat(1999 - i, 2, 7)));
            Student student3 = studentRepository.save(new Student("Filip", "Tomkow", getDateInFormat(1999 - i, 1, 15)));
            Student student4 = studentRepository.save(new Student("Michal", "Nos", getDateInFormat(1999 - i, 1, 15)));
            Student student5 = studentRepository.save(new Student("Emilia", "Krawczyk", getDateInFormat(1999 - i, 1, 15)));
            Student student6 = studentRepository.save(new Student("Hubert", "Klisza", getDateInFormat(1999 - i, 1, 15)));
            Student student7 = studentRepository.save(new Student("Lukasz", "Zieminski", getDateInFormat(1999 - i, 1, 15)));
            Student student8 = studentRepository.save(new Student("Karolina", "Kwiat", getDateInFormat(1999 - i, 1, 15)));
            Student student9 = studentRepository.save(new Student("Sebastian", "Gmyrek", getDateInFormat(1999 - i, 1, 15)));
            Student student10 = studentRepository.save(new Student("Dominika", "Wolna", getDateInFormat(1999 - i, 1, 15)));

            List<UserModel> userModelList = Arrays.asList(
                    student1UserModel, student2UserModel, student3UserModel, student4UserModel, student5UserModel, student6UserModel,
                    student7UserModel, student8UserModel, student9UserModel, student10UserModel
            );

            List<Student> students = Arrays.asList(
                    student1, student2, student3, student4, student5, student6, student7, student8, student9, student10
            );

            Exercise chemy = chemyList.get(i);
            Exercise math = mathList.get(i);
            Exercise geographic = geographicList.get(i);
            Exercise english = englishList.get(i);
            Exercise physic = physicsList.get(i);

            for (int j = 0; j < students.size(); j++) {
                Random random = new Random();
                Student student = students.get(j);
                UserModel userModel = userModelList.get(j);

                Grade examChemy = gradeRepository.save(new Grade(getDateInFormat(2018, 3, 17), random.nextInt(5) + 1, "Sprawdzian"));
                Grade smallExamChemy = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 21), random.nextInt(5) + 1, "Kartkowka"));
                Grade speachChemy = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 1), random.nextInt(5) + 1, "Odpowiedz"));
                Grade examMath = gradeRepository.save(new Grade(getDateInFormat(2018, 3, 17), random.nextInt(5) + 1, "Sprawdzian"));
                Grade smallExamMath = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 21), random.nextInt(5) + 1, "Kartkowka"));
                Grade speachMath = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 1), random.nextInt(5) + 1, "Odpowiedz"));
                Grade examGeo = gradeRepository.save(new Grade(getDateInFormat(2018, 3, 17), random.nextInt(5) + 1, "Sprawdzian"));
                Grade smallExamGeo = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 21), random.nextInt(5) + 1, "Kartkowka"));
                Grade speachGeo = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 1), random.nextInt(5) + 1, "Odpowiedz"));
                Grade examEng = gradeRepository.save(new Grade(getDateInFormat(2018, 3, 17), random.nextInt(5) + 1, "Sprawdzian"));
                Grade smallExamEng = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 21), random.nextInt(5) + 1, "Kartkowka"));
                Grade speachEng = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 1), random.nextInt(5) + 1, "Odpowiedz"));
                Grade examPhysic = gradeRepository.save(new Grade(getDateInFormat(2018, 3, 17), random.nextInt(5) + 1, "Sprawdzian"));
                Grade smallExamPhysic = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 21), random.nextInt(5) + 1, "Kartkowka"));
                Grade speachPhysic = gradeRepository.save(new Grade(getDateInFormat(2018, 2, 1), random.nextInt(5) + 1, "Odpowiedz"));

                userModel.setUserDetails(student);
                student.setUserModel(userModel);

                student.setSchoolClass(schoolClass);
                schoolClass.getStudents().add(student);

                student.getExercises().add(chemy);
                student.getExercises().add(math);
                student.getExercises().add(geographic);
                student.getExercises().add(english);
                student.getExercises().add(physic);
                chemy.getStudents().add(student);
                math.getStudents().add(student);
                geographic.getStudents().add(student);
                english.getStudents().add(student);
                physic.getStudents().add(student);

                student.getGrades().add(examChemy);
                student.getGrades().add(smallExamChemy);
                student.getGrades().add(speachChemy);
                student.getGrades().add(examMath);
                student.getGrades().add(smallExamMath);
                student.getGrades().add(speachMath);
                student.getGrades().add(examGeo);
                student.getGrades().add(smallExamGeo);
                student.getGrades().add(speachGeo);
                student.getGrades().add(examEng);
                student.getGrades().add(smallExamEng);
                student.getGrades().add(speachEng);
                student.getGrades().add(examPhysic);
                student.getGrades().add(smallExamPhysic);
                student.getGrades().add(speachPhysic);
                examChemy.setStudent(student);
                smallExamChemy.setStudent(student);
                speachChemy.setStudent(student);
                examMath.setStudent(student);
                smallExamMath.setStudent(student);
                speachMath.setStudent(student);
                examGeo.setStudent(student);
                smallExamGeo.setStudent(student);
                speachGeo.setStudent(student);
                examEng.setStudent(student);
                smallExamEng.setStudent(student);
                speachEng.setStudent(student);
                examPhysic.setStudent(student);
                smallExamPhysic.setStudent(student);
                speachPhysic.setStudent(student);

                examChemy.setExercise(chemy);
                smallExamChemy.setExercise(chemy);
                speachChemy.setExercise(chemy);
                chemy.getGrades().add(examChemy);
                chemy.getGrades().add(smallExamChemy);
                chemy.getGrades().add(speachChemy);
                examMath.setExercise(math);
                smallExamMath.setExercise(math);
                speachMath.setExercise(math);
                math.getGrades().add(examMath);
                math.getGrades().add(smallExamMath);
                math.getGrades().add(speachMath);
                examGeo.setExercise(geographic);
                smallExamGeo.setExercise(geographic);
                speachGeo.setExercise(geographic);
                geographic.getGrades().add(examGeo);
                geographic.getGrades().add(smallExamGeo);
                geographic.getGrades().add(speachGeo);
                examEng.setExercise(english);
                smallExamEng.setExercise(english);
                speachEng.setExercise(english);
                english.getGrades().add(examEng);
                english.getGrades().add(smallExamEng);
                english.getGrades().add(speachEng);
                examPhysic.setExercise(physic);
                smallExamPhysic.setExercise(physic);
                speachPhysic.setExercise(physic);
                physic.getGrades().add(examPhysic);
                physic.getGrades().add(smallExamPhysic);
                physic.getGrades().add(speachPhysic);

                userModelRepository.save(userModel);
                studentRepository.save(student);

                gradeRepository.save(examChemy);
                gradeRepository.save(smallExamChemy);
                gradeRepository.save(speachChemy);
                gradeRepository.save(examMath);
                gradeRepository.save(smallExamMath);
                gradeRepository.save(speachMath);
                gradeRepository.save(examGeo);
                gradeRepository.save(smallExamGeo);
                gradeRepository.save(speachGeo);
                gradeRepository.save(examEng);
                gradeRepository.save(smallExamEng);
                gradeRepository.save(speachEng);
                gradeRepository.save(examPhysic);
                gradeRepository.save(smallExamPhysic);
                gradeRepository.save(speachPhysic);

            }
            exerciseRepository.save(chemy);
            exerciseRepository.save(math);
            exerciseRepository.save(geographic);
            exerciseRepository.save(english);
            exerciseRepository.save(physic);

            schoolClassRepository.save(schoolClass);
        }

        UserModel userModel = new UserModel("admin@mail.com", "admin", "adminpass", UserRole.ADMIN.name());
        userModelRepository.save(userModel);
    }

    public Date getDateInFormat(int year, int month, int day) throws ParseException {
        String dateToParse = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateToParse);
    }
}
