package com.begoingto.springbootapi.api.accountype;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface AccountTypeMapper {
//    @Select("select * from account_types")
    @SelectProvider(type = AccountTypeProvider.class, method = "buildSelectSql")
    List<AccountType> select();
    @SelectProvider(type = AccountTypeProvider.class,method = "buildCreateSql")
    AccountType create(AccountTypeDto accountTypeDto);
}
