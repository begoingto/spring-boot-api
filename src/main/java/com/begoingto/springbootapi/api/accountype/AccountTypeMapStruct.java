package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapStruct {
    PageInfo<AccountTypeDto> toDto(PageInfo<AccountType> accountTypePageInfo);
    AccountTypeDto toDto(AccountType accountType);
    AccountType toDto(AccountTypeDto accountTypeDto);
}
