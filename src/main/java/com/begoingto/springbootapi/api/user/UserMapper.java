package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.account.Account;
import com.begoingto.springbootapi.api.user.web.Filters;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {

    @SelectProvider(type = UserProvider.class, method = "buildSelectSql")
    @Results(id = "userResult", value = {
            @Result(column = "id",property = "id"),
            @Result(column = "student_card_id",property = "studentCardId"),
            @Result(column = "is_student",property = "isStudent"),
            @Result(column = "id",property = "accounts", javaType = List.class, many = @Many(select = "selectAccountUser"))
    })
    List<User> select(@Param("f") Filters filters);

    @Select("SELECT ac.* FROM user_accounts as us RIGHT JOIN accounts as ac on ac.id=us.account_id WHERE us.user_id=#{id}")
    @Results(id = "accountMap", value = {
            @Result(column = "id",property = "id"),
            @Result(column = "account_no",property = "accountNo"),
            @Result(column = "account_name",property = "accountName"),
            @Result(column = "profile",property = "profile"),
            @Result(column = "phone_number",property = "phoneNumber"),
            @Result(column = "transfer_limit",property = "transferLimit")
    })
    public Account selectAccountUser(Integer id);

    @InsertProvider(type = UserProvider.class,method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(@Param("u") User user);

    @UpdateProvider(type = UserProvider.class,method = "buildUpdateSql")
    void updateById(@Param("u") User user);

    @SelectProvider(type = UserProvider.class, method = "buildSelectByIdSql")
//    @Result(column = "student_card_id",property = "studentCardId")
//    @Result(column = "is_student",property = "isStudent")
    @Results(id = "userResultMap", value = {
            @Result(column = "student_card_id",property = "studentCardId"),
            @Result(column = "is_student",property = "isStudent")
    })
    Optional<User> selectById(Integer id);

    @SelectProvider(type = UserProvider.class, method = "buildSelectByStudentCardIdSql")
    @ResultMap("userResultMap")
    Optional<User> selectByStdId(String stdId);

    @Select("SELECT EXISTS(SELECT * FROM users WHERE id=#{id})")
    boolean existUserById(@Param("id") Integer id);

    @DeleteProvider(type = UserProvider.class, method = "buildDeleteByIdSql")
    void deleteById(Integer id);

    @UpdateProvider(type = UserProvider.class,method = "buildUpdateIsDeletedById")
    void updateIsDeletedById(@Param("id") Integer id, @Param("status") boolean status);
}
