package com.begoingto.springbootapi.api.accountype;

import com.begoingto.springbootapi.api.accountype.web.AccountTypeDto;
import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    private final String table = "account_types";
    public String buildSelectSql(){
        return new SQL() {{
            SELECT("*");
            FROM(table);
            WHERE("is_deleted = FALSE");
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
            WHERE("id = #{id}","is_deleted = FALSE");
        }}.toString();
    }

    public String buildUpdateSql(){
        return new  SQL(){{
            UPDATE(table);
            SET("name=#{act.name}");
            WHERE("id = #{act.id}");
        }}.toString();
    }

    public String buildDeleteByIdSql(){
        return new SQL(){{
            UPDATE(table);
            SET("is_deleted = TRUE");
            WHERE("id = #{id}");
        }}.toString();
    }
}
