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

    public String buildRegisterCreateUserRoleSql(){
        return new SQL(){{
            INSERT_INTO("users_roles");
            VALUES("user_id","#{userId}");
            VALUES("role_id","#{roleId}");
        }}.toString();
    }

    public String buildSelectByEmailAndVerifiedCodeSql(){
        return new SQL(){{
            SELECT("*");
            FROM(TABLE);
            WHERE("email=#{email}","verified_code=#{code}");
        }}.toString();
    }

    public String buildUpdateIsVerifyStatusSql(){
        return new SQL(){{
            UPDATE(TABLE);
            SET("is_verified=TRUE");
            WHERE("email=#{email}","verified_code=#{code}");
        }}.toString();
    }


    public String buildUpdateVerifiedCodeSql(){
        return new SQL(){{
            UPDATE(TABLE);
            SET("verified_code=#{code}");
            WHERE("email=#{email}");
        }}.toString();
    }


    public String buildLoadUserRolesSql(){
        return new SQL(){{
            SELECT("r.id,r.name");
            FROM("roles AS r");
            INNER_JOIN("users_roles as ur ON ur.role_id=r.id");
            WHERE("ur.user_id=#{id}");
        }}.toString();
    }

}
