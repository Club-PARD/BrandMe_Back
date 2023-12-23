package com.soim.brandme.configuration;

import com.soim.brandme.user.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //IOC
public class SecurityConfig {
    @Autowired
    private Oauth2UserService oauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .requestMatchers("/user/**").authenticated()
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
                // hasRole('ROLE_USER')")
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
                // hasRole('ROLE_USER')")
                .requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .oauth2Login().userInfoEndpoint()
                .userService(oauth2UserService);

        return http.build();
    }
}
