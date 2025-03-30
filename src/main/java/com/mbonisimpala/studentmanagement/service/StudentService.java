package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Student;

import java.util.List;

public interface StudentService {

    Student getStudent(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);
    List<Student> getAllStudents();
}
