package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.user.web.CreateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;

    @Override
    public UserDto createNewUser(CreateUserDto createUserDto) {

        // Mapper createUser to User
        User user = userMapStruct.createUserDtoToUser(createUserDto);
        // insert user to database
        userMapper.insert(user);

        log.info("User = {}",user.getId());
        return userMapStruct.userToUserDto(user);
    }
}
