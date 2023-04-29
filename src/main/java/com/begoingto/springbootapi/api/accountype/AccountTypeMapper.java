package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AccountTypeMapper {
//    @Select("select * from account_types")
    @SelectProvider(type = AccountTypeProvider.class, method = "buildSelectSql")
    List<AccountType> select();
    @InsertProvider(type = AccountTypeProvider.class,method = "buildCreateSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void create(@Param("act") AccountType accountType);
}
