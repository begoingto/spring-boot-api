package com.begoingto.springbootapi.api.auth;

import org.apache.ibatis.jdbc.SQL;

public class AuthProvider
{
    private final String TABLE = "users";
    public String buildRegisterSql(){
        return new SQL(){{
            INSERT_INTO(TABLE);
            VALUES("email","#{u.email}");
            VALUES("password","#{u.password}");
            VALUES("is_verified","#{u.isVerified}");
            VALUES("is_deleted","FALSE");
        }}.toString();
    }
}
