package com.begoingto.springbootapi.api.account.web;

import com.begoingto.springbootapi.api.account.AccountService;
import com.begoingto.springbootapi.base.BaseRest;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;

    @GetMapping
    public BaseRest<?> get(
            @RequestParam(name ="page",required = false,defaultValue = "1") int page,
            @RequestParam(name ="limit",required = false,defaultValue = "10") int limit
    ){
        var accounts = accountService.getAll(page,limit);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Accounts have been found")
                .timestamp(LocalDateTime.now())
                .data(accounts)
                .build();
    }
}
