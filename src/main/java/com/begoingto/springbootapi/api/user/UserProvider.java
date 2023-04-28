package com.begoingto.springbootapi.api.user;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private final String table = "users";


    public String buildSelectSql(){
        return new SQL(){{
            SELECT("*");
            FROM(table);
            WHERE("is_deleted = FALSE");
            ORDER_BY("id DESC");
        }}.toString();
    }


    public String buildInsertSql() {
        return new SQL(){{
            INSERT_INTO(table);
            VALUES("name","#{u.name}");
            VALUES("gender","#{u.gender}");
            VALUES("one_signal_id","#{u.oneSignalId}");
            VALUES("student_card_id","#{u.studentCardId}");
            VALUES("is_student","#{u.isStudent}");
            VALUES("is_deleted","FALSE");
        }}.toString();
    }

    public String buildUpdateSql(){
        return new  SQL(){{
            UPDATE(table);
            SET("name=#{u.name}","gender=#{u.gender}");
            WHERE("id = #{u.id}");
        }}.toString();
    }

    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM(table);
            WHERE("id = #{id}", "is_deleted = FALSE");
        }}.toString();
    }

    public String buildDeleteByIdSql(){
        return new SQL(){{
            DELETE_FROM(table);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildUpdateIsDeletedById(){
        return new SQL(){{
            UPDATE(table);
            SET("is_deleted = #{status}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
