package com.mbonisimpala.studentmanagement.service;

import com.mbonisimpala.studentmanagement.entity.Grade;

import java.util.List;

public interface GradeService {
    Grade getGrade(Long studentId, Long courseId);
    Grade saveGrade(Grade grade, Long studentId, Long courseId);
    Grade updateGrade(String grade, Long studentId, Long courseId);
    void deleteGrade(Long studentId, Long courseId);
    List<Grade> getAllStudentGrades(Long studentId);
    List<Grade> getAllCourseGrade(Long courseId);
    List<Grade> getAllGrades();
}
