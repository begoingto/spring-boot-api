package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;

    @Override
    public List<AccountTypeDto> findAll() {

        List<AccountType> accountTypes = accountTypeMapper.select();
        /*List<AccountTypeDto> accountTypeDtoList = accountTypes.stream()
                .map(accountType -> new AccountTypeDto(accountType.getName()))
                .toList();*/

        return accountTypeMapStruct.toDto(accountTypes);
    }

    @Override
    public AccountTypeDto create(AccountTypeDto accountTypeDto) {
        AccountType accountType = accountTypeMapStruct.toDto(accountTypeDto);
        accountTypeMapper.create(accountType);
        System.out.println(accountType);
        return accountTypeMapStruct.toDto(accountType);
    }
}
