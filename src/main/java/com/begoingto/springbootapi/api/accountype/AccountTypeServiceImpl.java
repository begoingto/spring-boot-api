package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import com.begoingto.springbootapi.api.user.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public AccountTypeDto findById(Integer id) {
        AccountType accountType  = accountTypeMapper.selectById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Account Type with id=%d is not found.",id)
                )
        );
        return accountTypeMapStruct.toDto(accountType);
    }

    @Override
    public AccountTypeDto updateUserById(Integer id, AccountTypeDto accountTypeDto) {
        if (accountTypeMapper.existUserById(id)){
            AccountType accountType  = accountTypeMapStruct.toDto(accountTypeDto);
            accountType.setId(id);
            accountTypeMapper.updateById(accountType);
            return this.findById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with id=%d is not found.",id));
    }

    @Override
    public AccountTypeDto updateById(Integer id, AccountTypeDto accountTypeDto) {
        return null;
    }

    @Override
    public Integer deleteById(Integer id) {
        if (accountTypeMapper.existUserById(id)){
            accountTypeMapper.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("account Type with id=%d is not found.",id));
    }
}
