package com.mbonisimpala.studentmanagement.controller;

import com.mbonisimpala.studentmanagement.entity.Grade;
import com.mbonisimpala.studentmanagement.exception.ErrorResponse;
import com.mbonisimpala.studentmanagement.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Grade Controller", description = "Manages grade records for students and courses, including creation, update, retrieval, and deletion.")
@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    GradeService gradeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Retrieve a specific grade", description = "Fetches a grade for a specific student and course.")
    @GetMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> getGrade(@PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.getGrade(studentId, courseId), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grade created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Create a new grade", description = "Stores a new grade for a student in a specific course.")
    @PostMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> saveGrade(@RequestBody @Valid Grade grade, @PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.saveGrade(grade, studentId, courseId), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Update an existing grade", description = "Updates the grade for a student in a specific course.")
    @PutMapping(value = "/student/{studentId}/course/{courseId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> updateGrade(@RequestBody @Valid Grade grade, @PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.updateGrade(grade.getGrade(), studentId, courseId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "204", description = "Grade deleted successfully")
    @Operation(summary = "Delete a grade", description = "Deletes the grade entry for a student in a specific course.")
    @DeleteMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteGrade(@PathVariable Long studentId, @PathVariable Long courseId){
        gradeService.deleteGrade(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponse(responseCode = "200", description = "Student's grades retrieved successfully")
    @Operation(summary = "Retrieve all grades for a student", description = "Fetches all grades associated with a given student.")
    @GetMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long studentId){
        return new ResponseEntity<>(gradeService.getAllStudentGrades(studentId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Course grades retrieved successfully")
    @Operation(summary = "Retrieve all grades for a course", description = "Fetches all grades assigned for a specific course.")
    @GetMapping(value = "/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getCourseGrades(@PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.getAllCourseGrades(courseId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "All grades retrieved successfully")
    @Operation(summary = "Retrieve all grades", description = "Fetches every grade stored in the system.")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getAllGrades(){
        return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
    }
}

