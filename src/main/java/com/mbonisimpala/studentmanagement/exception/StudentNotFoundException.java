package com.mbonisimpala.studentmanagement.exception;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(Long studentId) {
        super("The grade with student id: '" + studentId + "' does not exist in our records." );
    }
}
