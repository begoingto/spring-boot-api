package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    private final String table = "account_types";
    public String buildSelectSql(){
        return new SQL() {{
            SELECT("*");
            FROM(table);
        }}.toString();
    }

    public String buildCreateSql(){
        return new SQL(){{
            INSERT_INTO(table);
            VALUES("name","#{act.name}");
        }}.toString();
    }

    public String selectById(){
        return new SQL(){{
            SELECT("*");
            FROM(table);
            WHERE("id = #{id}");
        }}.toString();
    }
}
