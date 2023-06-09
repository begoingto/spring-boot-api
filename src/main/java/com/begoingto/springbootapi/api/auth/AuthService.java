package com.begoingto.springbootapi.api.auth;

import com.begoingto.springbootapi.api.auth.web.LoginDto;
import com.begoingto.springbootapi.api.auth.web.LoginResponse;
import com.begoingto.springbootapi.api.auth.web.RegisterDto;

public interface AuthService {

    void register(RegisterDto registerDto);

    void verify(String email);

    void checkVerify(String email, String code);

    LoginResponse userLogin(LoginDto loginDto);
}
