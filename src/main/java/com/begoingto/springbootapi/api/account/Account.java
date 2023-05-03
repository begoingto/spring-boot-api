package com.begoingto.springbootapi.api.account;

import com.begoingto.springbootapi.api.accountype.AccountType;
import com.begoingto.springbootapi.api.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class Account {
    private Integer id;
    private String accountNo;
    private String accountName;
    private String profile;
    private Integer pin;
    private String password;
    private String phoneNumber;
    private Integer transferLimit;
    private AccountType accountType;
    private List<User> users;
}
