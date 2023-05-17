package com.begoingto.springbootapi.api.user.validator;

import com.begoingto.springbootapi.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailUniqueConstraintValidator implements ConstraintValidator<EmailUnique, String> {
    private final UserMapper userMapper;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userMapper.existByEmail(email);
    }
}
