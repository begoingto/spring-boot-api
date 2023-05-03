package com.begoingto.springbootapi.api.account;

import com.begoingto.springbootapi.api.account.web.AccountDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountMapStruct accountMapStruct;
    private final AccountMapper accountMapper;


    @Override
    public PageInfo<AccountDto> getAll(int page, int limit) {

        PageInfo<Account> accountPageInfo = PageHelper.startPage(page,limit).doSelectPageInfo(accountMapper::select);

        return accountMapStruct.accountsToAccountDtoPageInfo(accountPageInfo);
    }
}
