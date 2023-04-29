package com.begoingto.springbootapi.api.accountype;

import lombok.*;

@NoArgsConstructor
@Data
public class AccountType {
    private Integer id;
    private String name;
    private Boolean isDeleted;
}
