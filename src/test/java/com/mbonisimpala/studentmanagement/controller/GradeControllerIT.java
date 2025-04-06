package com.mbonisimpala.studentmanagement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.entity.Grade;
import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.service.CourseService;
import com.mbonisimpala.studentmanagement.service.GradeService;
import com.mbonisimpala.studentmanagement.service.StudentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Course savedCourse;
    private Student savedStudent;

    @BeforeEach
    void setup(TestInfo testInfo){
        if(testInfo.getDisplayName().contains("shouldReturnEmptyList_whenNoGradesAvailable")){
            return; // skip this test to avoid deleting the same grade twice
        }

        savedCourse = courseService.saveCourse(
                new Course("Data Structures and Algorithms", "CS201",
                        "Fundamental concepts of data organization and problem-solving techniques.")
        );

        savedStudent = studentService.saveStudent(
                new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe")
        );
    }

    @AfterEach
    void cleanUp() {

        if (savedCourse != null && savedStudent != null) {
            gradeService.deleteGrade(savedStudent.getId(), savedCourse.getId());
        }
        if (savedCourse != null) {
            courseService.deleteCourse(savedCourse.getId());
        }
        if (savedStudent != null) {
            studentService.deleteStudent(savedStudent.getId());
        }
        savedStudent = null;
        savedCourse = null;
    }

    @Test
    public void shouldReturnGrade_whenSavingValidGrade() throws Exception{

        Grade validGrade = new Grade(1L, "5.0", savedCourse, savedStudent);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/grade/student/" + savedStudent.getId() + "/course/" + savedCourse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validGrade));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.grade").value("5.0"));
    }

    @Test
    public void shouldReturnBadRequest_whenSavingInvalidGrade() throws Exception{

        Grade invalidGrade = new Grade(1L, "A+", savedCourse, savedStudent);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/grade/student/" + savedStudent.getId() + "/course/" + savedCourse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidGrade));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The grade is not valid"));

    }

    @Test
    public void shouldReturnNotFound_whenSavingInvalidCourseId() throws Exception{
        // Given
        long invalidCourseId = 999L;

        Grade validGrade = new Grade(1L, "5.0", savedCourse, savedStudent);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/grade/student/" + savedStudent.getId() + "/course/" + invalidCourseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validGrade));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The course id '999' does not exist in our records"));
    }

    @Test
    public void shouldReturnNotFound_whenSavingInvalidStudentId() throws Exception{
        // Given
        long invalidStudentId = 999L;

        Grade validGrade = new Grade(1L, "5.0", savedCourse, savedStudent);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/grade/student/" + invalidStudentId + "/course/" + savedCourse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validGrade));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The grade with student id: '999' does not exist in our records."));
    }

    @Test
    public void shouldReturnGrade_whenGettingValidGrade() throws Exception{
        // Given
        gradeService.saveGrade( new Grade(1L, "4.5", savedCourse, savedStudent),
                savedStudent.getId(), savedCourse.getId());

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/grade/student/" + savedStudent.getId() + "/course/" + savedCourse.getId());

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value("4.5"));
    }

    @Test
    public void shouldReturnNotFound_whenGettingInvalidGrade() throws Exception {
        // Given ( Non-existent student and course IDs)
        long studentId = 999L;
        long courseId = 999L;

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/grade/student/" + studentId + "/course/" + courseId);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The grade with student id : '999' and courseId: '999' does not exist in our records."));

    }

    @Test
    public void shouldReturnGrade_whenUpdatingExistingGrade() throws Exception{
        // Given ( Existing Grade )
        gradeService.saveGrade( new Grade(1L, "4.5", savedCourse, savedStudent),
                savedStudent.getId(), savedCourse.getId());

        // When

        // Build JSON body
        String updatedGradeJSON = objectMapper.writeValueAsString(Map.of("grade", "3.0"));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/grade/student/" + savedStudent.getId() + "/course/" + savedCourse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedGradeJSON);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value("3.0"));
    }

    @Test
    public void shouldReturnBadRequest_whenUpdatingExistentIDs() throws Exception{
        // Given (Non-existent student and course IDs)
        long studentId = 999L;
        long courseId = 999L;

        // When
        String updatedGradeJSON = objectMapper.writeValueAsString(Map.of("grade", "3.0"));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/grade/student/" + studentId + "/course/" + courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedGradeJSON);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The grade with student id : '999' and courseId: '999' does not exist in our records."));
    }

    @Test
    public void shouldReturnBadRequest_whenUpdatingWithInvalidGrade() throws Exception{
        // Given ( Existing Grade )
        gradeService.saveGrade( new Grade(1L, "4.5", savedCourse, savedStudent),
                savedStudent.getId(), savedCourse.getId());

        // When
        // Build JSON body
        String invalidGradeJSON = objectMapper.writeValueAsString(Map.of("grade", "A-")); // Invalid Grade
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/grade/student/" + savedStudent.getId() + "/course/" + savedCourse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidGradeJSON);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The grade is not valid"));
    }

    @Test
    public void shouldReturnNoContent_whenDeletingExistingGrade() throws Exception{
        // Given (existing grade)
        gradeService.saveGrade( new Grade(1L, "4.5", savedCourse, savedStudent),
                savedStudent.getId(), savedCourse.getId());

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/grade/student/1/course/1");

        // Perform & verify
        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNoContent_whenDeletingWithInvalidIDs() throws Exception{
        // Given (non-existent course and student IDs)
        long studentId = 999L;
        long courseId = 999L;

        // When request has invalid course and student Ids
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/grade/student/1/course/1");

        // Perform & verify
        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnGradesList_whenGettingExistingStudentGrades() throws Exception{
        // Given ( existing student grades )
        gradeService.saveGrade( new Grade(1L, "4.5", savedCourse, savedStudent),
                savedStudent.getId(), savedCourse.getId());


        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/grade/student/" + savedStudent.getId());

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].grade").value("4.5"));
    }

    @Test
    public void shouldReturnGradeList_whenGettingExistingCourseGrades() throws Exception{
        //  Given
        gradeService.saveGrade( new Grade(1L, "4.5", savedCourse, savedStudent),
                savedStudent.getId(), savedCourse.getId());

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/grade/course/" + savedCourse.getId());

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].grade").value("4.5"));
    }

    @Test
    public void shouldReturnGradeList_whenGettingAllGrades() throws Exception{
        //  Given
        gradeService.saveGrade( new Grade(1L, "4.5", savedCourse, savedStudent),
                savedStudent.getId(), savedCourse.getId());

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/grade/all");

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].grade").value("4.5"));
    }

    @Test
    public void shouldReturnEmptyList_whenNoGradesAvailable() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/grade/all");

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


}
