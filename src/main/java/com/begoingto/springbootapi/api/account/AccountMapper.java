package com.begoingto.springbootapi.api.account;

import com.begoingto.springbootapi.api.accountype.AccountType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {
    @SelectProvider(type = AccountProvider.class, method = "buildSelectSql")
    @Results(id = "accountResultMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "account_no",property = "accountNo"),
            @Result(column = "account_name",property = "accountName"),
            @Result(column = "profile",property = "profile"),
            @Result(column = "phone_number",property = "phoneNumber"),
            @Result(column = "transfer_limit",property = "transferLimit"),
            @Result(property = "accountType",javaType = AccountType.class,column = "account_type",one = @One(select = "getAccountType")),
    })
    List<Account> select();


    @Select("SELECT * FROM account_types WHERE id=#{accountType}")
    AccountType getAccountType(Integer accountType);
}
