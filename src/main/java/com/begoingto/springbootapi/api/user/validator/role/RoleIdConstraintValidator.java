package com.begoingto.springbootapi.api.user.validator.role;

import com.begoingto.springbootapi.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class RoleIdConstraintValidator implements ConstraintValidator<RoleIdConstraint, List<Integer>> {
    private final UserMapper userMapper;
    @Override
    public boolean isValid(List<Integer> roleIds, ConstraintValidatorContext context) {
        Set<Integer> UniqueId = new HashSet<>(roleIds);
        for (Integer id: UniqueId){
            if (!userMapper.checkRoleId(id)) return false;
        }

        return true;
    }
}
