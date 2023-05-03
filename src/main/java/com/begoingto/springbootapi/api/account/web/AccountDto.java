package com.begoingto.springbootapi.api.account.web;

import com.begoingto.springbootapi.api.accountype.AccountType;
import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;

public record AccountDto(
        Integer id,
        String accountNo,
        String accountName,
        String profile,
        String phoneNumber,
        Integer transferLimit,
        AccountTypeDto accountType
) {
}
