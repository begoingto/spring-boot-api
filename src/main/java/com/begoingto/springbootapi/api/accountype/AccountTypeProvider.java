package com.begoingto.springbootapi.api.accountype;

import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    private final String table = "account_types";
    public String buildSelectSql(){
        return new SQL() {{
            SELECT("*");
            FROM(table);
        }}.toString();
    }

    public String buildCreateSql(AccountTypeDto accountTypeDto){
        String name = accountTypeDto.name();
        return new SQL(){{
            INSERT_INTO(table);
            INTO_COLUMNS("name");
            INTO_VALUES("#{name}");
        }}.toString();
    }
}
