package com.begoingto.springbootapi.api.user.web;

import com.begoingto.springbootapi.api.user.UserService;
import com.begoingto.springbootapi.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public  BaseRest<?> findAllUser(
            @RequestParam(name = "page",required = false,defaultValue = "1") int page,
            @RequestParam(name = "limit",required = false,defaultValue = "15") int limit,
            @RequestParam(name = "name",required = false,defaultValue = "") Filters filters
    ){
        PageInfo<UserDto> userDtoPageInfo = userService.findAllUser(page, limit, filters);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been get successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDtoPageInfo)
                .build();
    }

    @PostMapping
    BaseRest<?> createUser(@RequestBody @Valid CreateUserDto createUserDto){

        UserDto userDto = userService.createNewUser(createUserDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been created successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }

    @GetMapping("/{id}")
    BaseRest<?> findUserById(@PathVariable Integer id){
        UserDto userDto = userService.findUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been found successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }

    @GetMapping("/{stdId}/student-card-id")
    BaseRest<?> findUserById(@PathVariable String stdId){
        UserDto userDto = userService.findUserByStudentCardId(stdId);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been found successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }


    @PutMapping("/{id}")
    public BaseRest<?> updateUser(@PathVariable Integer id,@RequestBody UpdateUserDto updateUserDto){
        UserDto userDto = userService.updateUserById(id,updateUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been update successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }

    @DeleteMapping("/{id}")
    BaseRest<?> deleteUserById(@PathVariable Integer id){

        Integer userId = userService.deleteUserById(id);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been deleted successfully.")
                .timestamp(LocalDateTime.now())
                .data(userId)
                .build();
    }

    @PutMapping("/{id}/is-delete")
    BaseRest<?> updateIsDeletedById(@PathVariable Integer id,@RequestBody IsDeletedDto isDeletedDto){

        Integer userId = userService.updateIsDeletedStatus(id, isDeletedDto.status());

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been updated status successfully.")
                .timestamp(LocalDateTime.now())
                .data(userId)
                .build();
    }
}
