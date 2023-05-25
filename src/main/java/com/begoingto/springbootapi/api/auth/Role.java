package com.begoingto.springbootapi.api.auth;

import com.begoingto.springbootapi.api.user.Authority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {
    private Integer id;
    private String name;
    private Set<Authority> authorities;

    @Override
    public String getAuthority() {
        return "ROLE_"+ name; // spring need require prefix ROLE_
    }
}
