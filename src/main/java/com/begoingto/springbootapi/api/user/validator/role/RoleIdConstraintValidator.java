package com.begoingto.springbootapi.api.user.validator.role;

import com.begoingto.springbootapi.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class RoleIdConstraintValidator implements ConstraintValidator<RoleIdConstraint, Set<Integer>> {
    private final UserMapper userMapper;
    @Override
    public boolean isValid(Set<Integer> roleIds, ConstraintValidatorContext context) {

        for (Integer id: roleIds){
            if (!userMapper.checkRoleId(id)) return false;
        }

        return true;
    }
}
