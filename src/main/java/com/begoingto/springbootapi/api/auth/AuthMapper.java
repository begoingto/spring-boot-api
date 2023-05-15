package com.begoingto.springbootapi.api.auth;

import com.begoingto.springbootapi.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AuthMapper {

    @InsertProvider(type = AuthProvider.class, method = "buildRegisterSql")
    void register(@Param("u") User user);

    @Select("SELECT * FROM users WHERE email=#{e} AND is_deleted=FALSE")
    @Results(id = "authResult", value = {
            @Result(column = "id",property = "id"),
            @Result(column = "student_card_id",property = "studentCardId"),
            @Result(column = "is_student",property = "isStudent"),
            @Result(column = "is_verified",property = "isVerified"),
            @Result(column = "verified_code",property = "verifiedCode")
    })
    Optional<User> selectByEmail(@Param("e") String email);

    @SelectProvider(type = AuthProvider.class, method = "buildSelectByEmailAndVerifiedCodeSql")
    @ResultMap("authResult")
    Optional<User> selectByEmailAndVerifiedCode(@Param("email") String email,@Param("code") String verifiedCode);

    @UpdateProvider(type = AuthProvider.class, method = "buildUpdateIsVerifyStatusSql")
    void updateIsVerifyStatus(@Param("email") String email,@Param("code") String verifiedCode);

    @UpdateProvider(type = AuthProvider.class, method = "buildUpdateVerifiedCodeSql")
    boolean updateVerifiedCode(@Param("email") String email,@Param("code") String verifiedCode);
}
