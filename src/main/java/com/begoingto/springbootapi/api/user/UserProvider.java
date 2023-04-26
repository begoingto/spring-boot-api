package com.begoingto.springbootapi.api.user;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private final String table = "users";

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

}
