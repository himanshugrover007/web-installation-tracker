package com.luv2code.springdemo.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern.Flag;

public class BlankOrPatternValidator implements ConstraintValidator<BlankOrPattern, String> {

    private Pattern pattern;

    public void initialize(BlankOrPattern parameters) {
        Flag flags[] = parameters.flags();
        int intFlag = 0;
        for (Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        try {
            pattern = Pattern.compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid regular expression.", e);
        }
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}