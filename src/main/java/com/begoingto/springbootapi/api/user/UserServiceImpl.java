package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.user.web.CreateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;

    @Override
    public PageInfo<UserDto> findAllUser(int page, int limit) {

         PageInfo<User> userPageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(userMapper::select);

        return userMapStruct.userPageInfoToUserDtoPageInfo(userPageInfo);
    }

    @Override
    public UserDto createNewUser(CreateUserDto createUserDto) {

        // Mapper createUser to User
        User user = userMapStruct.createUserDtoToUser(createUserDto);
        // insert user to database
        userMapper.insert(user);

        log.info("User = {}",user.getId());
        System.out.println("User ID: "+ user.getId());
        return this.findUserById(user.getId());
    }

    @Override
    public UserDto findUserById(Integer id) {
        User user  = userMapper.selectById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id=%d is not found.",id)
                        )
        );

        return userMapStruct.userToUserDto(user);
    }

    @Override
    public Integer deleteUserById(Integer id) {
        boolean isFound = userMapper.existUserById(id);
        if (isFound){
            userMapper.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with id=%d is not found.",id));
    }

    @Override
    public Integer updateIsDeletedStatus(Integer id, boolean status) {
        boolean isFound = userMapper.existUserById(id);
        if (isFound){
            userMapper.updateIsDeletedById(id, status);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with id=%d is not found.",id));
    }
}
