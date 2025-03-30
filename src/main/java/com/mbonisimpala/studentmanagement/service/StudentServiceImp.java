package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.exception.StudentNotFoundException;
import com.mbonisimpala.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        return unwrapStudent(studentRepository.findById(id), id);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    static Student unwrapStudent(Optional<Student> entity, Long id){
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
    }
}
