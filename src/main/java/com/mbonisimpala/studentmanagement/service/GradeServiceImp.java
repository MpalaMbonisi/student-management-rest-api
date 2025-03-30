package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.entity.Grade;
import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.repository.CourseRepository;
import com.mbonisimpala.studentmanagement.repository.GradeRepository;
import com.mbonisimpala.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImp implements GradeService{

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Grade getGrade(Long studentId, Long courseId) {
        return gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();
        grade.setStudent(student);
        grade.setCourse(course);
        return gradeRepository.save(grade);
    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {
        gradeRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public Grade updateGrade(String grade, Long studentId, Long courseId) {
        Grade gradeObject = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        gradeObject.setGrade(grade);
        return gradeRepository.save(gradeObject);
    }

    @Override
    public List<Grade> getAllStudentGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    @Override
    public List<Grade> getAllCourseGrade(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getAllGrades() {
        return (List<Grade>) gradeRepository.findAll();
    }
}