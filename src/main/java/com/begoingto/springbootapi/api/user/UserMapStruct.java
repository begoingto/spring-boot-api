package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.user.web.CreateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    User createUserDtoToUser(CreateUserDto createUserDto);
    UserDto userToUserDto(User user);
    User userToUserDto(UserDto user);
    PageInfo<UserDto> userPageInfoToUserDtoPageInfo(PageInfo<User> userPageInfo);
}
