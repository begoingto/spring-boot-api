package com.begoingto.springbootapi.api.account.web;

import com.begoingto.springbootapi.api.accountype.AccountType;

public record AccountDto(
        Integer id,
        String accountNo,
        String accountName,
        String profile,
        String phoneNumber,
        Integer transferLimit,
        AccountType accountType
) {
}
