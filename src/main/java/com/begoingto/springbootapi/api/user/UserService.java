package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.user.web.CreateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;

public interface UserService {

    PageInfo<UserDto> findAllUser(int page, int limit);

    UserDto createNewUser(CreateUserDto createUserDto);

    UserDto findUserById(Integer id);

    Integer deleteUserById(Integer id);

    Integer updateIsDeletedStatus(Integer id,boolean status);
}
