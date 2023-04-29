package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;

    @Override
    public PageInfo<AccountTypeDto> findAll(int page,int limit) {
        PageInfo<AccountType> accountTypePageInfo = PageHelper.startPage(page,limit).doSelectPageInfo(accountTypeMapper::select);
        return accountTypeMapStruct.toDto(accountTypePageInfo);
    }

    @Override
    public AccountTypeDto create(AccountTypeDto accountTypeDto) {
        AccountType accountType = accountTypeMapStruct.toDto(accountTypeDto);
        accountTypeMapper.create(accountType);
        System.out.println(accountType);
        return accountTypeMapStruct.toDto(accountType);
    }
}
