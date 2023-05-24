package com.begoingto.springbootapi.api.auth;

import com.begoingto.springbootapi.api.auth.web.LoginDto;
import com.begoingto.springbootapi.api.auth.web.LoginResponse;
import com.begoingto.springbootapi.api.auth.web.RegisterDto;
import com.begoingto.springbootapi.api.user.User;
import com.begoingto.springbootapi.api.user.UserMapStruct;
import com.begoingto.springbootapi.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private PasswordEncoder encoder;
    private final MailUtil mailUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder jwtEncoder;

    @Value("${spring.mail.username}")
     private String appMail;

    @Autowired
    void setEncoder(PasswordEncoder encoder){
        this.encoder = encoder;
    }


    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        User user  = userMapStruct.registerDtoToUser(registerDto);
        user.setPassword(encoder.encode(registerDto.password()));
        user.setIsVerified(false);

        log.info("User: {}", user.getEmail());

        if (authMapper.register(user)){
            // Create role
            for (Integer roleId: registerDto.roleIds()){
                authMapper.createUserRole(user.getId(),roleId);
            }
        }

    }


    @Override
    public void verify(String email) {

        User user  = authMapper.selectByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Email not found."));

        String verifiedCode = UUID.randomUUID().toString();

        if (authMapper.updateVerifiedCode(email, verifiedCode)){
            user.setVerifiedCode(verifiedCode);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"User cannot be verify.");
        }


        MailUtil.Meta<?> meta  = MailUtil.Meta.builder()
                .to(email)
                .from(appMail)
                .subject("Account Verify")
                .templateUrl("mail/verify")
                .data(user)
                .build();
        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @Override
    public void checkVerify(String email, String code) {
        User user  = authMapper.selectByEmailAndVerifiedCode(email,code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Email not found."));
        if (!user.getIsVerified()){
            authMapper.updateIsVerifyStatus(email,code);
        }
    }

    @Override
    public LoginResponse userLogin(LoginDto loginDto) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password());

        authentication = daoAuthenticationProvider.authenticate(authentication);



        List<SimpleGrantedAuthority> authorities = new ArrayList<>(){{
//            add(new SimpleGrantedAuthority("WRITE"));
            add(new SimpleGrantedAuthority("READ"));
//            add(new SimpleGrantedAuthority("DELETE"));
//            add(new SimpleGrantedAuthority("UPDATE"));
//            add(new SimpleGrantedAuthority("FULL_CONTROL"));
        }};

        // Define scope
        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("scope", scope)
                .build();

        String jwtEndCode = this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();


        User user  = authMapper.selectByEmail(loginDto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Email not found."));


        /*
        *System.out.println(authentication);



        if (!encoder.matches(loginDto.password(), user.getPassword())){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You are wrong password");
        }

        String basicFormat = authentication.getName()+ ":" + authentication.getCredentials();
        String encoding = Base64.getEncoder().encodeToString(basicFormat.getBytes());*/

        return LoginResponse.builder()
                .tokenType("Bearer")
                .accessToken(jwtEndCode)
                .user(user)
                .build();
    }
}
