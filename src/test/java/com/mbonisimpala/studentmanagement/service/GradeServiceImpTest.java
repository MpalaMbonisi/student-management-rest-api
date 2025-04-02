package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.entity.Grade;
import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.exception.GradeNotFoundException;
import com.mbonisimpala.studentmanagement.repository.CourseRepository;
import com.mbonisimpala.studentmanagement.repository.GradeRepository;
import com.mbonisimpala.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/*
- method test naming convention used
- methodName_StateUnderTest_ExpectedBehavior()
*/

@ExtendWith(MockitoExtension.class)
class GradeServiceImpTest {
    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    GradeRepository gradeRepository;

    @InjectMocks
    GradeServiceImp gradeService;

    @Test
    public void getGrade_ExistingId_ShouldReturnGrade(){
        // Arrange
        Long courseId = 1L;
        Long studentId = 2L;

        Course mockCourse = new Course("Data Structures and Algorithms", "CS201",
                "Fundamental concepts of data organization and problem-solving techniques.");

        Student mockStudent = new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe");

        Grade grade = new Grade(3L, "5.0", mockCourse, mockStudent);
        when(gradeRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.of(grade));

        // Act
        Grade result = gradeService.getGrade(studentId, courseId);

        // Assert
        Assertions.assertAll("Grade properties",
                () -> Assertions.assertEquals("5.0", result.getGrade(), "Incorrect grade value"),
                () -> Assertions.assertEquals("CS201", result.getCourse().getCode(), "Incorrect course code"),
                () -> Assertions.assertEquals("Mbonisi", result.getStudent().getFirstName(), "Incorrect student name")
        );;
    }

    @Test
    public void getGrade_NonExistingStudentId_ShouldThrowException(){
        // Arrange
        Long courseId = 1L; // Existing ID
        Long studentId = 99L; // Non-existing ID

        // Act & Assert
        Assertions.assertThrows(GradeNotFoundException.class, () -> gradeService.getGrade(studentId, courseId));
    }

    @Test
    public void getGrade_NonExistingCourseId_ShouldThrowException(){
        // Arrange
        Long courseId = 99L; // Non-existing ID
        Long studentId = 1L; // Existing ID

        // Act & Assert
        Assertions.assertThrows(GradeNotFoundException.class, () -> gradeService.getGrade(studentId, courseId));
    }

    @Test
    public void saveGrade_ValidGrade_ShouldReturnGrade(){
        // Arrange
        Long courseId = 1L;
        Course mockCourse = new Course("Data Structures and Algorithms", "CS201",
                "Fundamental concepts of data organization and problem-solving techniques.");

        courseRepository.save(mockCourse);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));

        Long studentId = 1L;
        Student mockStudent = new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe");
        studentRepository.save(mockStudent);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));


        Grade grade = new Grade(3L, "5.0", mockCourse, mockStudent);
        when(gradeRepository.save(grade)).thenReturn(grade);

        // Act
        Grade result = gradeService.saveGrade(grade, studentId, courseId);

        // Arrange
        Assertions.assertAll("Grade properties",
                () -> Assertions.assertEquals("5.0", result.getGrade(), "Incorrect grade value"),
                () -> Assertions.assertEquals("CS201", result.getCourse().getCode(), "Incorrect course code"),
                () -> Assertions.assertEquals("Mbonisi", result.getStudent().getFirstName(), "Incorrect student name")
        );
    }

    @Test
    public void updateGrade_ExistingId_ShouldReturnGrade(){
        // Arrange
        Long courseId = 1L;
        Long studentId = 1L;

        Course mockCourse = new Course("Data Structures and Algorithms", "CS201",
                "Fundamental concepts of data organization and problem-solving techniques.");

        Student mockStudent = new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe");

        Grade grade = new Grade(1L, "4.5", mockCourse, mockStudent);

        when(gradeRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.of(grade));
        grade.setGrade("3.0");
        when(gradeRepository.save(grade)).thenReturn(grade);

        // Act
        Grade result = gradeService.updateGrade("3.0", studentId, courseId);

        // Assertions
        Assertions.assertEquals("3.0", result.getGrade(), "Incorrect grade");
    }

    @Test
    public void updateGrade_NonExistingStudentId_ShouldThrowException(){
        // Arrange
        Long courseId = 1L;
        Long studentId = 99L; // Non-existing ID

        // Act & Assert
        Assertions.assertThrows(GradeNotFoundException.class, () -> gradeService.updateGrade("4.0", studentId, courseId));
    }

    @Test
    public void updateGrade_NonExistingCourseId_ShouldThrowException(){
        // Arrange
        Long courseId = 99L; // Non-existing ID
        Long studentId = 1L;

        // Act & Assert
        Assertions.assertThrows(GradeNotFoundException.class, () -> gradeService.updateGrade("4.0", studentId, courseId));
    }

    @Test
    public void deleteGrade_ExistingId_ShouldCallRepositoryDelete(){
        // Arrange
        Long studentId = 1L;
        Long courseId = 1L;

        // Act
        gradeService.deleteGrade(studentId, courseId);

        // Assertions
        verify(gradeRepository, times(1)).deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Test
    public void deleteGrade_NonExistingCourseId_ShouldCallRepositoryDelete(){
        // Arrange
        Long studentId = 1L;
        Long courseId = 99L; // Non-existing ID

        // Act
        gradeService.deleteGrade(studentId, courseId);

        // Assertions
        verify(gradeRepository, times(1)).deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Test
    public void deleteGrade_NonExistingStudentId_ShouldCallRepositoryDelete(){
        // Arrange
        Long studentId = 99L; // Non-existing ID
        Long courseId = 1L;

        // Act
        gradeService.deleteGrade(studentId, courseId);

        // Assertions
        verify(gradeRepository, times(1)).deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Test
    public void getAllStudentGrades_ExistingGrades_ShouldReturnGradeList(){
        // Arrange
        Long studentId = 1L;
        Student mockStudent = new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe");

        List<Course> coursesList = List.of(new Course[] {
                new Course("Data Structures and Algorithms", "CS201", "Fundamental concepts of data organization and problem-solving techniques."),
                new Course("Operating Systems", "CS301", "Introduction to process management, memory management, and file systems."),
                new Course("Cybersecurity Fundamentals", "CS305", "Introduction to cybersecurity concepts, encryption, and threat mitigation.")
        });

        List<Grade> gradesList = List.of(new Grade[]{
                new Grade(1L, "4.0", coursesList.get(0), mockStudent),
                new Grade(1L, "3.0", coursesList.get(1), mockStudent),
                new Grade(1L, "4.5", coursesList.get(2), mockStudent)
        });

        when(gradeRepository.findByStudentId(studentId)).thenReturn(gradesList);


        // Act
        List<Grade> results = gradeService.getAllStudentGrades(studentId);

        // Assert
        Assertions.assertEquals(gradesList.size(), results.size(), "Incorrect list size.");
        Assertions.assertEquals(gradesList, results, "Incorrect list");
    }

    @Test
    public void getAllStudentGrades_NoGrades_ShouldReturnEmptyList(){
        // Arrange
        Long studentId = 1L;
        when(gradeRepository.findByStudentId(studentId)).thenReturn(List.of());

        // Act
        List<Grade> results = gradeService.getAllStudentGrades(studentId);

        // Assert
        Assertions.assertTrue(results.isEmpty(),"List returned not empty.");
    }

    @Test
    public void getAllCourses_ExistingGrades_ShouldReturnGradeList(){
        // Arrange
        Long courseId = 1L;
        Course mockCourse = new Course("Data Structures and Algorithms", "CS201",
                "Fundamental concepts of data organization and problem-solving techniques.");

        List<Student> studentsList = List.of(new Student[]{
                new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe"),
                new Student("Piotr", "Kamil", "Kowalski", LocalDate.parse("2006-05-20"), "Poland"),
                new Student("Agata", "Olga", "Duda", LocalDate.parse("2000-01-01"), "Ukraine")
        });

        List<Grade> gradesList = List.of(new Grade[]{
                new Grade(1L, "4.0", mockCourse, studentsList.get(0)),
                new Grade(1L, "3.0", mockCourse, studentsList.get(1)),
                new Grade(1L, "4.5", mockCourse, studentsList.get(2))
        });

        when(gradeRepository.findByCourseId(courseId)).thenReturn(gradesList);

        // Act
        List<Grade> results = gradeService.getAllCourseGrades(courseId);

        // Assert
        Assertions.assertEquals(gradesList.size(), results.size(), "Incorrect list size.");
        Assertions.assertEquals(gradesList, results, "Incorrect list");

    }

    @Test
    public void getAllCourseGrades_NoGrades_ShouldReturnEmptyList(){
        // Arrange
        Long courseId = 1L;
        when(gradeRepository.findByCourseId(courseId)).thenReturn(List.of());

        // Act
        List<Grade> results = gradeService.getAllCourseGrades(courseId);

        // Assert
        Assertions.assertTrue(results.isEmpty(), "List returned is not empty");
    }

    @Test
    public void getAllGrades_ExistingGrades_ShouldReturnGradeList(){
        // Arrange
        List<Course> coursesList = List.of(new Course[] {
                new Course("Data Structures and Algorithms", "CS201", "Fundamental concepts of data organization and problem-solving techniques."),
                new Course("Operating Systems", "CS301", "Introduction to process management, memory management, and file systems."),
                new Course("Cybersecurity Fundamentals", "CS305", "Introduction to cybersecurity concepts, encryption, and threat mitigation.")
        });

        List<Student> studentsList = List.of(new Student[]{
                new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe"),
                new Student("Piotr", "Kamil", "Kowalski", LocalDate.parse("2006-05-20"), "Poland"),
                new Student("Agata", "Olga", "Duda", LocalDate.parse("2000-01-01"), "Ukraine")
        });

        List<Grade> gradesList = List.of(new Grade[]{
                new Grade(1L, "4.0", coursesList.get(0), studentsList.get(0)),
                new Grade(1L, "3.0", coursesList.get(1), studentsList.get(1)),
                new Grade(1L, "4.5", coursesList.get(2), studentsList.get(2))
        });

        when(gradeRepository.findAll()).thenReturn(gradesList);

        // Act
        List<Grade> results = gradeService.getAllGrades();

        // Assert
        Assertions.assertEquals(gradesList.size(), results.size(), "Incorrect list size.");
        Assertions.assertEquals(gradesList, results, "Incorrect list");
    }

    @Test
    public void getAllGrades_NoGrades_ShouldReturnEmptyList(){
        // Arrange
        when(gradeRepository.findAll()).thenReturn(List.of());

        // Act
        List<Grade> results = gradeService.getAllGrades();

        // Assert
        Assertions.assertTrue(results.isEmpty(), "List returned is not empty");
    }

}