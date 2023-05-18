package com.begoingto.springbootapi.api.user.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchConstraintValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String pwd;
    private String confirmPwd;
    private String msg;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.pwd = constraintAnnotation.password();
        this.confirmPwd = constraintAnnotation.confirmPassword();
        this.msg = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object pwdValue = new BeanWrapperImpl(value).getPropertyValue(this.pwd);
        Object confirmPwdValue = new BeanWrapperImpl(value).getPropertyValue(this.confirmPwd);
        boolean isValid = false;

        if (pwdValue != null) {
            isValid = pwdValue.equals(confirmPwdValue);
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.msg)
                    .addPropertyNode(this.pwd)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.msg)
                    .addPropertyNode(this.confirmPwd)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
