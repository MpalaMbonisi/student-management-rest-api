package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.exception.CourseNotFoundException;
import com.mbonisimpala.studentmanagement.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

/*
- method test naming convention used
- methodName_StateUnderTest_ExpectedBehavior()
*/

@ExtendWith(MockitoExtension.class)
class CourseServiceImpTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseServiceImp courseService;

    @Test
    public void getCourse_ExistingId_ShouldReturnCourse(){
        // Arrange
        Long courseId = 1L;
        Course mockCourse = new Course("Data Structures and Algorithms", "CS201",
                "Fundamental concepts of data organization and problem-solving techniques.");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));

        // Act
        Course result = courseService.getCourse(courseId);

        // Assert
        Assertions.assertEquals(mockCourse.getCode(), result.getCode());
        Assertions.assertEquals(mockCourse.getDescription(), result.getDescription());
        Assertions.assertEquals(mockCourse.getSubject(), result.getSubject());
    }

    @Test
    public void getCourse_NonExistingId_ShouldThrowException(){
        // Arrange
        Long courseId = 99L; // Non-existent ID

        // Act & Assert
        Assertions.assertThrows(CourseNotFoundException.class, () -> courseService.getCourse(courseId));
    }

    @Test
    public void saveCouse_ValidCourse_ShouldReturnSavedCourse(){
        // Arrange
        Course mockCourse = new Course("Data Structures and Algorithms", "CS201",
                "Fundamental concepts of data organization and problem-solving techniques.");

        when(courseRepository.save(mockCourse)).thenReturn(mockCourse);

        // Act
        Course result = courseService.saveCourse(mockCourse);

        // Assert
        Assertions.assertEquals(mockCourse.getCode(), result.getCode());
        Assertions.assertEquals(mockCourse.getDescription(), result.getDescription());
        Assertions.assertEquals(mockCourse.getSubject(), result.getSubject());
    }

    @Test
    public void getAllCourses_ExistingCourses_ShouldReturnCourseList(){
        // Arrange
        List<Course> coursesList = List.of(new Course[] {
                new Course("Data Structures and Algorithms", "CS201", "Fundamental concepts of data organization and problem-solving techniques."),
                new Course("Operating Systems", "CS301", "Introduction to process management, memory management, and file systems."),
                new Course("Cybersecurity Fundamentals", "CS305", "Introduction to cybersecurity concepts, encryption, and threat mitigation.")
        });
        when(courseRepository.findAll()).thenReturn(coursesList);

        // Act
        List<Course> resultList = courseService.getAllCourses();

        // Assert
        Assertions.assertEquals(coursesList, resultList);
        Assertions.assertEquals(coursesList.size(), resultList.size());
    }

    @Test
    public void getAllCourses_NoCourses_ShouldReturnEmptyList(){
        // Arrange
        when(courseRepository.findAll()).thenReturn(List.of());

        // Act
        List<Course> result = courseService.getAllCourses();

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteCourse_ShouldCallRepositoryDelete(){
        // Arrange
        Long courseId = 1L;

        // Act
        courseService.deleteCourse(courseId);

        // Assert
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    public void deleteStudent_NotFound_ShouldStillCallDelete(){
        // Arrange
        Long courseId = 99L; // Non-existent ID

        // Act
        courseService.deleteCourse(courseId);

        // Assert
        verify(courseRepository, times(1)).deleteById(courseId);
    }

}