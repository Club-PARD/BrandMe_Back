package com.soim.brandme.configuration;

import com.soim.brandme.auth.application.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import static com.soim.brandme.auth.application.JwtUtil.createJwt;
import static com.soim.brandme.auth.application.JwtUtil.decodeJwt;

@Configuration //IOC
public class SecurityConfig {
    @Autowired
    private Oauth2UserService oauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    RememberMeServices rememberMeServices(UserDetailsService userDetailsService){
//        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
//        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("snorlax", userDetailsService,encodingAlgorithm);
//        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
//        return rememberMe;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http/*, RememberMeServices rememberMeServices*/) throws Exception {
        http.csrf().disable();
//        http.rememberMe((remember) -> remember.rememberMeServices(rememberMeServices));
        http.authorizeRequests()
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("swagger-ui.html").authenticated().anyRequest().permitAll()
                .and()
//                .rememberMe() //자동로그인
//                .rememberMeParameter("remember-me")
//                .tokenValiditySeconds(604800) // 7일
//                .alwaysRemember(false)
//                .and()
                .formLogin().disable() // formLogin을 사용하지 않겠다.
                .httpBasic().disable() // httpBasic을 사용하지 않겠다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않겠다
                .and()
                .oauth2Login().userInfoEndpoint()
                .userService(oauth2UserService)
                .and()
                .successHandler((request, response, authentication) -> {
                    String jwt = createJwt(authentication);
                    response.setHeader("Authorization", "Bearer " + jwt);
                    response.sendRedirect("http://localhost:3000/name");
                    decodeJwt(jwt);
                });

        return http.build();
    }

}
