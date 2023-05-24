package com.begoingto.springbootapi.api.user.web;

import com.begoingto.springbootapi.api.account.web.AccountDto;
import com.begoingto.springbootapi.api.auth.Role;

import java.util.List;

public record UserDto(
        Integer id,
        String name,
        String gender,
        String studentCardId,
        Boolean isStudent,
        List<AccountDto> accounts,

        List<Role> roles
) {
}
