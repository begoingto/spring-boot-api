package com.begoingto.springbootapi.api.user.web;

import com.begoingto.springbootapi.api.user.UserService;
import com.begoingto.springbootapi.base.BaseRest;
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
                .message("User have been find successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }
}
