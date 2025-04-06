package com.mbonisimpala.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.service.CourseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This annotation ensures that the application context is fully loaded
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Course savedCourse, secondCourse;

    @BeforeEach
    void setup(TestInfo testInfo){
        if(testInfo.getDisplayName().contains("shouldReturnEmptyList_whenNoCoursesAvailable")){
            return; // skip setup for this test
        }

        savedCourse = courseService.saveCourse(
                new Course("Data Structures and Algorithms", "CS201",
                        "Fundamental concepts of data organization and problem-solving techniques.")
        );
    }

    @AfterEach
    void cleanup(TestInfo testInfo){
        if(testInfo.getDisplayName().contains("shouldReturnNoContent_whenDeletingValidCourseId")){
            return; // skip this test to avoid deleting the same course twice
        }

        if(savedCourse != null){
            courseService.deleteCourse(savedCourse.getId());
            savedCourse = null; // avoid variable overlap
        }
        if(secondCourse != null){
            courseService.deleteCourse(secondCourse.getId());
            secondCourse = null; // avoid variable overlap
        }
    }

    @Test
    public void shouldReturnCourse_whenGettingValidCourseId() throws Exception{
        // When (Perform the action
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/course/" + savedCourse.getId());

        // Then verify (verify the output)
        mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Data Structures and Algorithms"));

    }

    @Test
    public void shouldReturnNotFound_whenGettingInvalidCourseId() throws Exception {
        // Given (Non-existent course ID)
        long invalidCourseId = 99L;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/course/" + invalidCourseId);

        mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The course id '99' does not exist in our records" ));
    }

    @Test
    public void shouldReturnCourse_whenSavingValidCourse() throws Exception{
        // When (build the request)
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedCourse));

        // Then (perform the request and verify response)
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subject").value("Data Structures and Algorithms"))
                .andExpect(jsonPath("$.code").value("CS201"));
    }

    @Test
    public void shouldReturnBadRequest_whenSavingInvalidCourse() throws Exception{
        // Given
        Course invalidCourse = new Course(" ", " ", " ");

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCourse));

        // Perform and verify
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message.length()").value(3))
                .andExpect(jsonPath("$.message", hasItems(
                        "Subject cannot be blank",
                        "Course code cannot be blank",
                        "Description cannot be blank"
                )));
    }

    @Test
    public void shouldReturnNoContent_whenDeletingValidCourseId() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/course/" + savedCourse.getId());

        // Perform and verify
        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFound_whenDeletingInvalidCourseId() throws Exception{
        // Given
        long invalidCourseId = 99L;

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/course/" + invalidCourseId);

        // Perform and verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("Cannot delete non-existing resource"));
    }

    @Test
    public void shouldReturnCourseList_whenGettingAllCourses() throws Exception{
        // Given (another course saved)
        secondCourse = courseService.saveCourse(
                new Course("Introduction to Databases", "CS204",
                        "Fundamental concepts of data organization and relational databases."));


        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/course/all");

        // Perform and verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].subject").value("Data Structures and Algorithms"))
                .andExpect(jsonPath("$[0].code").value("CS201"))
                .andExpect(jsonPath("$[1].subject").value("Introduction to Databases"))
                .andExpect(jsonPath("$[1].code").value("CS204"));
    }

    @Test
    public void shouldReturnEmptyList_whenNoCoursesAvailable() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/course/all");

        // Perform and verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


}
