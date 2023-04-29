package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import com.begoingto.springbootapi.api.user.web.UpdateUserDto;
import com.begoingto.springbootapi.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountTypeService {
    PageInfo<AccountTypeDto> findAll(int page,int limit);

    AccountTypeDto create(AccountTypeDto accountTypeDto);

    AccountTypeDto findById(Integer id);

    AccountTypeDto updateUserById(Integer id, AccountTypeDto accountTypeDto);

    AccountTypeDto updateById(Integer id, AccountTypeDto accountTypeDto);

    Integer deleteById(Integer id);
}
