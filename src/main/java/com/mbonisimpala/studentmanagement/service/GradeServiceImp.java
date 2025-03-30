package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Grade;
import com.mbonisimpala.studentmanagement.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImp implements GradeService{

    @Autowired
    GradeRepository gradeRepository;

    @Override
    public Grade getGrade(Long studentId, Long courseId) {
        return null;
    }

    @Override
    public Grade saveGrade(Grade grade) {
        return null;
    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {

    }

    @Override
    public Grade updateGrade(String grade, Long studentId, Long courseId) {
        return null;
    }

    @Override
    public List<Grade> getAllStudentGrades(Long studentId) {
        return null;
    }

    @Override
    public List<Grade> getAllCourseGrade(Long courseId) {
        return null;
    }

    @Override
    public List<Grade> getAllGrades() {
        return null;
    }
}