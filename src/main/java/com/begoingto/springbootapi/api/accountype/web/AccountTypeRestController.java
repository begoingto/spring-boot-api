package com.begoingto.springbootapi.api.accountype.web;

import com.begoingto.springbootapi.api.accountype.AccountTypeService;
import com.begoingto.springbootapi.api.user.web.UpdateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import com.begoingto.springbootapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_account:read')")
    public BaseRest<?> findAll(
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "limit",defaultValue = "15") int limit
    ){
        var accounts = accountTypeService.findAll(page, limit);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(accounts)
                .build();
    }

    @PostMapping
    public BaseRest<?> create(@RequestBody AccountTypeDto accountTypeDto){
        AccountTypeDto account = accountTypeService.create(accountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been create successfully.")
                .timestamp(LocalDateTime.now())
                .data(account)
                .build();
    }



    @GetMapping("/{id}")
    public BaseRest<?> selectById(@PathVariable int id){
        AccountTypeDto accountTypeDto = accountTypeService.findById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account type have been found successfully.")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDto)
                .build();
    }

    @PutMapping("/{id}")
    public BaseRest<?> updateUser(@PathVariable Integer id,@RequestBody AccountTypeDto accountTypeDto){
        AccountTypeDto account = accountTypeService.updateUserById(id,accountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account type have been update successfully.")
                .timestamp(LocalDateTime.now())
                .data(account)
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteById(@PathVariable int id){
        Integer actId = accountTypeService.deleteById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account Type have been deleted successfully.")
                .timestamp(LocalDateTime.now())
                .data(actId)
                .build();
    }
}
