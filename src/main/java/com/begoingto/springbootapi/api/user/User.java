package com.begoingto.springbootapi.api.user;

import com.begoingto.springbootapi.api.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private Integer id;
    private String name;
    private String gender;
    private String oneSignalId;
    private String studentCardId;
    private Boolean isStudent;
    private Boolean isDeleted;
    private List<Account> accounts;

    // Auth Feature
    private String email;
    private String password;
    private Boolean isVerified;
    private String verifiedCode;
}
