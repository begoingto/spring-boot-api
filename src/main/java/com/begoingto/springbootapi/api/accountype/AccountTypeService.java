package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeDto> findAll();

    AccountTypeDto create(AccountTypeDto accountTypeDto);
}
