package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.user.web.CreateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;

public interface UserService {

    UserDto createNewUser(CreateUserDto createUserDto);

    UserDto findUserById(Integer id);

}
