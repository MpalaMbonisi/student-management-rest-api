package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService{

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }
}
