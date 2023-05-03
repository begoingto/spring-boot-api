package com.begoingto.springbootapi.api.account;

import com.begoingto.springbootapi.api.account.web.AccountDto;
import com.github.pagehelper.PageInfo;

public interface AccountService {
    PageInfo<AccountDto> getAll(int page, int limit);
}
