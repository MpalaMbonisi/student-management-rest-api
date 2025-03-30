package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.entity.Grade;
import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.exception.GradeNotFoundException;
import com.mbonisimpala.studentmanagement.repository.CourseRepository;
import com.mbonisimpala.studentmanagement.repository.GradeRepository;
import com.mbonisimpala.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        return unwrapGrade(grade, studentId, courseId);
    }

    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        Student student = StudentServiceImp.unwrapStudent(studentRepository.findById(studentId), studentId);
        Course course = CourseServiceImp.unwrapCourse(courseRepository.findById(courseId), courseId);
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
        Optional<Grade> gradeObject = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        Grade unwrappedGrade = unwrapGrade(gradeObject, studentId, courseId);
        unwrappedGrade.setGrade(grade);
        return gradeRepository.save(unwrappedGrade);
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

    private Grade unwrapGrade(Optional<Grade> entity, Long studentId, Long courseId){
        if (entity.isPresent()) return entity.get();
        else throw new GradeNotFoundException(studentId, courseId);
    }
}