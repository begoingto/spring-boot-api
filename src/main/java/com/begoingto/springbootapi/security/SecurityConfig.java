package com.begoingto.springbootapi.security;

import com.begoingto.springbootapi.util.KeyUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomerAuthenticationEntryPoint customerAuthenticationEntryPoint;
    private final CustomJwtAccessDeniedHandler customJwtAccessDeniedHandler;

    private final KeyUtil keyUtil;

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

    // Define user authentication with jdbc database
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(encoder);
        return auth;
    }

    // The matched against an HttpServletRequest intercept
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
//        http.csrf(token -> token.disable());

        http.cors(cors -> cors.configure(http));

        http.authorizeHttpRequests(request -> {
            // any authenticate can access Any Role
            //request.requestMatchers("/api/v1/users/**").authenticated();

            //Authorize URL mapping
            /*request.requestMatchers("/api/v1/users/**").hasRole("ADMIN");
            request.requestMatchers("/api/v1/account-types/**","/api/v1/files/**").hasAnyRole("CUSTOMER","SYSTEM");
            request.anyRequest().permitAll();*/

            request.requestMatchers("/api/v1/auth/**").permitAll();
//            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyRole("ADMIN");
//            request.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAnyRole("SYSTEM");

//            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAuthority("SCOPE_ROLE_ADMIN");
//            request.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAuthority("SCOPE_ROLE_SYSTEM");

//            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyAuthority("SCOPE_READ","SCOPE_FULL_CONTROL");
//            request.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAnyAuthority("SCOPE_WRITE","SCOPE_FULL_CONTROL");
//            request.requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasAnyAuthority("SCOPE_UPDATE","SCOPE_FULL_CONTROL");
//            request.requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasAnyAuthority("SCOPE_DELETE","SCOPE_FULL_CONTROL");

            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAuthority("SCOPE_user:read");
            request.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAuthority("SCOPE_user:write");
            request.requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasAuthority("SCOPE_user:update");
            request.requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasAuthority("SCOPE_user:delete");
            request.anyRequest().authenticated();
        });


        //Security mechanism
//        http.httpBasic();
//        http.oauth2ResourceServer(oauth-> oauth.jwt());
//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        // configure Session Auth to stateless By default is stateful
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // make security stateless
//                .formLog/in(); //  using for normal web without api


        // Exception
        http.exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(customerAuthenticationEntryPoint);
                    ex.accessDeniedHandler(customJwtAccessDeniedHandler);
                });
//                .accessDeniedHandler(customJwtAccessDeniedHandler)
//                .authenticationEntryPoint(customerAuthenticationEntryPoint);

        return http.build();
    }

    @Bean(name = "jwtAccessTokenDecoder")
    @Primary
    public JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getAccessTokenPublicKey()).build();
    }

    @Bean(name = "jwtAccessTokenEncoder")
    @Primary
    public JwtEncoder jwtAccessTokenEncoder(){
        JWK jwk = new RSAKey.Builder((keyUtil.getAccessTokenPublicKey()))
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }



    @Bean(name = "jwtRefreshTokenDecoder")
    public JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getAccessTokenPublicKey()).build();
    }

    @Bean(name = "jwtRefreshTokenEncoder")
    public JwtEncoder jwtRefreshTokenEncoder(){
        JWK jwk = new RSAKey.Builder((keyUtil.getRefreshTokenPublicKey()))
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }


/*
    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    public RSAKey rsaKey(KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    */

    @Bean
    public JWKSource<SecurityContext> jwkSource(){
        JWK jwk = new RSAKey.Builder((keyUtil.getRefreshTokenPublicKey()))
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(jwk);
        /**
         * return  new JWKSource<SecurityContext>() {
         *             @Override
         *             public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
         *                 return jwkSelector.select(jwkSet);
         *             }
         *         };
         * */
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return new JwtAuthenticationConverter();
    }
}
