package com.begoingto.springbootapi.api.account;

import org.apache.ibatis.jdbc.SQL;

public class AccountProvider {

    private final String table = "accounts";

    public String buildSelectSql(){
        return new SQL(){{
            SELECT("*");
            FROM(table);
        }}.toString();
    }
}
