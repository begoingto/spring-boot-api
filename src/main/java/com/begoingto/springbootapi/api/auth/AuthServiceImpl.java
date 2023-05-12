package com.begoingto.springbootapi.api.auth;

import com.begoingto.springbootapi.api.auth.web.RegisterDto;
import com.begoingto.springbootapi.api.user.User;
import com.begoingto.springbootapi.api.user.UserMapStruct;
import com.begoingto.springbootapi.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private PasswordEncoder encoder;

    private final MailUtil mailUtil;

    @Autowired
    void setEncoder(PasswordEncoder encoder){
        this.encoder = encoder;
    }


    @Override
    public void register(RegisterDto registerDto) {
        User user  = userMapStruct.registerDtoToUser(registerDto);
        user.setPassword(encoder.encode(registerDto.password()));
        user.setIsVerified(false);

        log.info("User: {}", user.getEmail());
        authMapper.register(user);
    }

    @Override
    public void verify(String email) {

        User user  = authMapper.selectByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Email not found."));

        user.setVerifiedCode(UUID.randomUUID().toString());

        MailUtil.Meta<?> meta  = MailUtil.Meta.builder()
                .to(email)
                .from("begoingto.me@gamil.com")
                .subject("Account Verify")
                .templateUrl("mail/verify")
                .data(user)
                .build();
        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
