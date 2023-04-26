package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.user.web.CreateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;

    @Override
    public UserDto createNewUser(CreateUserDto createUserDto) {

        User user = userMapStruct.createUserDtoToUser(createUserDto);
        userMapper.insert(user);

        return userMapStruct.userToUserDto(user);
    }
}
