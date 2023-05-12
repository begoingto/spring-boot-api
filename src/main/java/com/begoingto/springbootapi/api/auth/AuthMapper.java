package com.begoingto.springbootapi.api.auth;

import com.begoingto.springbootapi.api.user.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface AuthMapper {

    @InsertProvider(type = AuthProvider.class, method = "buildRegisterSql")
    void register(@Param("u") User user);

    @Select("SELECT * FROM users WHERE email=#{e}")
    Optional<User> selectByEmail(@Param("e") String email);
}
