package com.soim.brandme.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
@Slf4j
public class JwtUtil {
    private static String SECRET_KEY;
    @Autowired
    public JwtUtil(Environment env) {
        JwtUtil.SECRET_KEY = env.getProperty("custom.jwt.secret");
    }

    private static final long EXPIRATION_TIME = 604800000;




    public static String createJwt(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        return Jwts.builder()
                .setSubject(email) // 주제 설정 (이메일)
                .claim("name", name) // 사용자 이름 포함
                .setIssuedAt(new Date()) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)// 서명 알고리즘 및 키 설정
                .compact();
    }

    public static void decodeJwt(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwtToken)
                    .getBody();

            log.info("JWT Claims: {}", claims);
        } catch (Exception e) {
            log.error("JWT Parsing Error", e);
        }
    }
}
