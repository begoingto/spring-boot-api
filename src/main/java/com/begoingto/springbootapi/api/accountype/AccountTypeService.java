package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AccountTypeService {
    PageInfo<AccountTypeDto> findAll(int page,int limit);

    AccountTypeDto create(AccountTypeDto accountTypeDto);
}
