package com.mbonisimpala.studentmanagement.exception;

public class GradeNotFoundException extends RuntimeException{

    public GradeNotFoundException(Long studentId, Long courseId){
        super("The grade with student id : '" + studentId + "' and courseId: '" +
                courseId + "' does not exist in our records.");
    }

}
