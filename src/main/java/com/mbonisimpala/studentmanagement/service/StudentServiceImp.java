package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImp implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        return null;
    }

    @Override
    public Student saveStudent(Student student) {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {

    }

    @Override
    public List<Student> getAllStudents() {
        return null;
    }
}
