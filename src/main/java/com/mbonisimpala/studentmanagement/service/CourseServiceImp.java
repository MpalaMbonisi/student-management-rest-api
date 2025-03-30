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
        return null;
    }

    @Override
    public Course saveCourse(Course course) {
        return null;
    }

    @Override
    public void deleteCourse(Long id) {

    }

    @Override
    public List<Course> getAllCourses() {
        return null;
    }
}
