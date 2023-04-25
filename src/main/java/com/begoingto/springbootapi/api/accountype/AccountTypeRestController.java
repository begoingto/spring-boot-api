package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;
    @GetMapping
    public BaseRest<?> findAll(){
        var accounts = accountTypeService.findAll();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(accounts)
                .build();
    }

    @PostMapping
    public AccountTypeDto create(@RequestBody AccountTypeDto accountTypeDto){
        var account = accountTypeService.create(accountTypeDto);
        System.out.println(account);
        return accountTypeDto;
    }
}
