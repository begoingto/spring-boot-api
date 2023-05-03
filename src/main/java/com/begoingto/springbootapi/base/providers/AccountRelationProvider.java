package com.begoingto.springbootapi.base.providers;

import com.begoingto.springbootapi.api.account.Account;
import com.begoingto.springbootapi.api.account.AccountProvider;
import com.begoingto.springbootapi.api.accountype.AccountType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountRelationProvider {
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
    public List<Account> selectAccounts();

    @Select("SELECT ac.* FROM user_accounts as us RIGHT JOIN accounts as ac on ac.id=us.account_id WHERE us.user_id=#{id}")
    @ResultMap("accountResultMap")
    public Account selectUserAccounts(Integer id);

    @Select("SELECT * FROM account_types WHERE id=#{accountType}")
    public AccountType getAccountType(Integer accountType);
}
