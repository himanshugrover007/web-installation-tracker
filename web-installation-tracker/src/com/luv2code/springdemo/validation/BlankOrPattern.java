package com.luv2code.springdemo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern.Flag;

@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BlankOrPatternValidator.class)
public @interface BlankOrPattern {
    String regexp();
    Flag[] flags() default {};
    String message() default "{javax.validation.constraints.Pattern.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}