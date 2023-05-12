package com.begoingto.springbootapi.api.auth;

import com.begoingto.springbootapi.api.auth.web.RegisterDto;

public interface AuthService {

    void register(RegisterDto registerDto);

    void verify(String email);
}
