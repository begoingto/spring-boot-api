package com.begoingto.springbootapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsService;

    // Define in-memory user
    /* @Bean
    public UserDetailsService userDetailsService(){
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails admin = User.builder()
                .username("admin")
//                .password("{noop}123")
                .password(encoder.encode("123"))
                .roles("ADMIN")
                .build();
        UserDetails goldUser = User.builder()
                .username("gold")
                .password(encoder.encode("123"))
                //.password("{noop}123")
                .roles("ACCOUNT")
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("123"))
                //.password("{noop}123")
                .roles("USER")
                .build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(goldUser);
        return userDetailsManager;
    }*/

    // Define user auth with jdbc db
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(encoder);
        return auth;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
//        http.csrf(token -> token.disable());

        http.authorizeHttpRequests(request -> {
            // any authenticate can access Any Role
            //request.requestMatchers("/api/v1/users/**").authenticated();

            //Authorize URL mapping
            /*request.requestMatchers("/api/v1/users/**").hasRole("ADMIN");
            request.requestMatchers("/api/v1/account-types/**","/api/v1/files/**").hasAnyRole("CUSTOMER","SYSTEM");
            request.anyRequest().permitAll();*/

            request.requestMatchers("/api/v1/auth/**").permitAll();
            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyRole("ADMIN");
            request.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAnyRole("SYSTEM");
            request.anyRequest().authenticated();
        });


        //Security mechanism
        http.httpBasic();

        // configure Session Auth to stateless By default is stateful
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // make security stateless
//                .formLog/in(); // using for normal web without api
        return http.build();
    }

}
