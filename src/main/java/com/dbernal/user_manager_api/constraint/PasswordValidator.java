package com.dbernal.user_manager_api.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    @Value("${validate.password.pattern.regexp}")
    private String passwordPattern;

    @Value("${validate.password.error.message}")
    private String errorMessage;

    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Pattern p = Pattern.compile(passwordPattern);
        Matcher m = p.matcher(password);

        if (m.matches()) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(errorMessage)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;

    }
}
