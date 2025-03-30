package com.mbonisimpala.studentmanagement.controller;

import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id){
        return new ResponseEntity<>(courseService.getCourse(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> saveCourse(@RequestBody Course course){
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }
}
