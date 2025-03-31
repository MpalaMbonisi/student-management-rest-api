package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.exception.StudentNotFoundException;
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
class StudentServiceImpTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImp studentService;

    @Test
    public void getStudent_ExistingId_ShouldReturnStudent(){
        // Arrange
        Long studentId = 1L;
        Student mockStudent = new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
        // Act
        Student result = studentService.getStudent(studentId);

        // Assert
        Assertions.assertEquals(mockStudent.getFirstName(), result.getFirstName());
        Assertions.assertEquals(mockStudent.getLastName(), result.getLastName());
        Assertions.assertEquals(mockStudent.getBirthDate(), result.getBirthDate());
        Assertions.assertEquals(mockStudent.getNationality(), result.getNationality());
    }

    @Test
    public void getStudent_NotFound_ShouldThrowException(){
        // Arrange
        Long studentId = 99L; // Non-existent ID

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudent(studentId));
    }

    @Test
    public void saveStudent_ValidStudent_ShouldReturnSavedStudent(){
        // Arrange
        Student mockStudent = new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe");

        when(studentRepository.save(mockStudent)).thenReturn(mockStudent);

        // Act
        Student result = studentService.saveStudent(mockStudent);

        // Assert
        Assertions.assertEquals(mockStudent.getFirstName(), result.getFirstName());
        Assertions.assertEquals(mockStudent.getLastName(), result.getLastName());
        Assertions.assertEquals(mockStudent.getBirthDate(), result.getBirthDate());
        Assertions.assertEquals(mockStudent.getNationality(), result.getNationality());
    }

    @Test
    public void getAllStudents_ExistingStudents_ShouldReturnStudentList(){
        // Arrange
        List<Student> mockStudents = List.of(new Student[]{
                new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe"),
                new Student("Piotr", "Kamil", "Kowalski", LocalDate.parse("2006-05-20"), "Poland"),
                 new Student("Agata", "Olga", "Duda", LocalDate.parse("2000-01-01"), "Ukraine")
        });

        when(studentRepository.findAll()).thenReturn(mockStudents);

        // Act
        List<Student> results = studentService.getAllStudents();

        // Assert
        Assertions.assertEquals(mockStudents.size(), results.size());
        Assertions.assertEquals(mockStudents, results);
    }

    @Test
    public void getAllStudents_NoStudents_ShouldReturnEmptyList(){
        // Arrange
        when(studentRepository.findAll()).thenReturn(List.of());

        // Act
        List<Student> results = studentService.getAllStudents();

        // Assert
        Assertions.assertTrue(results.isEmpty());

    }

    @Test
    public void deleteStudent_ShouldCallRepositoryDelete(){
        // Arrange
        Long studentId = 1L;

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    public void deleteStudent_NotFound_ShouldStillCallDelete(){
        // Arrange
        Long studentId = 99L; // non-existent ID

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        verify(studentRepository, times(1)).deleteById(studentId);
    }

}