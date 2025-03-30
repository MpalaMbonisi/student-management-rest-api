package com.mbonisimpala.studentmanagement.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class GradeValidator implements ConstraintValidator<ValidGrade, String> {

    List<String> grades = Arrays.asList(
            "5.0", "4.5", "4.0",
            "3.5", "3.0", "2.0"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null) return false;
        for(String grade: grades){
            if(value.equals(grade)) return true;
        }
        return false;
    }
}
