package com.begoingto.springbootapi.api.accountype;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Optional;

@Mapper
public interface AccountTypeMapper {
//    @Select("select * from account_types")
    @SelectProvider(type = AccountTypeProvider.class, method = "buildSelectSql")
    List<AccountType> select();

    @InsertProvider(type = AccountTypeProvider.class,method = "buildCreateSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void create(@Param("act") AccountType accountType);

    @SelectProvider(type = AccountTypeProvider.class, method = "selectById")
    @Results(id = "actResultMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "name",property = "name")
    })
    Optional<AccountType> selectById(Integer id);
}
