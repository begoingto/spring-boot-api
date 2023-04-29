package com.begoingto.springbootapi.api.accountype.web;

import com.begoingto.springbootapi.api.accountype.AccountTypeService;
import com.begoingto.springbootapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;
    @GetMapping
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
                .message("User have been found successfully.")
                .timestamp(LocalDateTime.now())
                .data(account)
                .build();
    }
}
