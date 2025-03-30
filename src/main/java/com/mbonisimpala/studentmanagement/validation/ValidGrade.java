package com.mbonisimpala.studentmanagement.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GradeValidator.class)
public @interface ValidGrade {
    String message() default "The grade is not valid";
    Class<?> [] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
