package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.user.web.CreateUserDto;
import com.begoingto.springbootapi.api.user.web.Filters;
import com.begoingto.springbootapi.api.user.web.UpdateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    PageInfo<UserDto> findAllUser(int page, int limit, Filters filters);

    UserDto createNewUser(CreateUserDto createUserDto);

    UserDto updateUserById(Integer id, UpdateUserDto updateUserDto);

    UserDto findUserById(Integer id);

    Integer deleteUserById(Integer id);

    Integer updateIsDeletedStatus(Integer id,boolean status);

    UserDto findUserByStudentCardId(String studentCardId);
}
