package com.begoingto.springbootapi.api.accountype;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeDto> findAll();

    AccountTypeDto create(AccountTypeDto accountTypeDto);
}
