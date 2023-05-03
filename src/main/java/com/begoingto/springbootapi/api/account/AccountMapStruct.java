package com.begoingto.springbootapi.api.account;

import com.begoingto.springbootapi.api.account.web.AccountDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapStruct {
    PageInfo<AccountDto> accountsToAccountDtoPageInfo(PageInfo<Account> accountPageInfo);

}
