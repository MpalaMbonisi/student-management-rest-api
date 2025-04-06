package com.mbonisimpala.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbonisimpala.studentmanagement.entity.Student;
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

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student savedStudent, secondStudent;

    @BeforeEach
    void setup(TestInfo testInfo){
        if(testInfo.getDisplayName().contains("shouldReturnEmptyList_whenNoStudentsAvailable")){
            return; // skip setup for this test
        }
        savedStudent = studentService.saveStudent(
                new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe")
        );
    }

    @AfterEach
    void cleanup(TestInfo testInfo){
        if(testInfo.getDisplayName().contains("shouldReturnNoContent_whenDeletingValidStudentId")){
            return; // skip this test to avoid deleting the same student twice
        }
        if (savedStudent != null){
            studentService.deleteStudent(savedStudent.getId());
            savedStudent = null; // avoid variable overlap
        }
        if(secondStudent != null){
            studentService.deleteStudent(secondStudent.getId());
            secondStudent = null; // avoid variable overlap
        }
    }

    @Test
    public void shouldReturnStudent_whenGettingValidStudentId() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/student/" + savedStudent.getId());

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mbonisi"))
                .andExpect(jsonPath("$.lastName").value("Mpala"))
                .andExpect(jsonPath("$.nationality").value("Zimbabwe"));

    }

    @Test
    public void shouldReturnNotFound_whenGettingInvalidStudentId() throws Exception{
        // Given (Non-existent student ID)
        long invalidStudentId = 99L;

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/student/" + invalidStudentId);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The grade with student id: '99' does not exist in our records."));

    }

    @Test
    public void shouldReturnStudent_WhenSavingValidStudent() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedStudent));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Mbonisi"))
                .andExpect(jsonPath("$.lastName").value("Mpala"))
                .andExpect(jsonPath("$.nationality").value("Zimbabwe"));
    }

    @Test
    public void shouldReturnBadRequest_whenSavingInvalidStudent() throws Exception{
        // Given
        Student invalidStudent = new Student("", "", "", LocalDate.parse("2040-12-16"),"");

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStudent));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.length()").value(4))
                .andExpect(jsonPath("$.message", hasItems(
                        "The birth day must be in the past",
                        "Last name cannot be blank",
                        "The nationality cannot be blank",
                        "First name cannot be blank"
                )));
    }

    @Test
    public void shouldReturnNoContent_whenDeletingValidStudentId() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/student/" + savedStudent.getId());

        // Perform & verify
        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFound_whenDeletingInvalidStudentId() throws Exception{
        // Given (non-existent student id)
        long invalidStudentId = 99L;

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/student/" + invalidStudentId);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("Cannot delete non-existing resource"));
    }

    @Test
    public void shouldReturnStudentList_whenGettingAllStudents() throws Exception{
        // Given (that second student saved)
        secondStudent = studentService.saveStudent(new Student("John", "Peter", "Smith", LocalDate.parse("2004-02-14"), "United Kingdom"));

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/student/all");

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].middleName").value("Peter"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].nationality").value("United Kingdom"));
    }

    @Test
    public void shouldReturnEmptyList_whenNoStudentsAvailable() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/student/all");

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

}
