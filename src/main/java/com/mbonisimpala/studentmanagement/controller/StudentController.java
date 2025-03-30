package com.mbonisimpala.studentmanagement.controller;

import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }
}
