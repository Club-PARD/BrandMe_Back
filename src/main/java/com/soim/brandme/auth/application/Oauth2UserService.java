package com.soim.brandme.auth.application;

import com.soim.brandme.auth.domain.GoogleUserInfo;
import com.soim.brandme.auth.domain.OAuth2UserInfo;
import com.soim.brandme.user.domain.User;
import com.soim.brandme.user.presentation.request.UserRequest;
import com.soim.brandme.user.domain.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class Oauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepo userRepo;

//    userRequest는 code를 받아서 토큰을 발급받고, 토큰을 통해 회원정보를 가져오는 역할을 한다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); //google의 회원정보를 받아온다.

        // code를 통해 구성한 정보
        // System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        log.info("############OAUTH2USER############");
        // token을 통해 응답받은 회원정보
        log.info("oAuth2User : " + oAuth2User);
        log.info("############OAUTH2USER############");
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
//        Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        Optional<User> userOptional =
                userRepo.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        User user;
        if(userOptional.isPresent()) {
//            user정보 존재하면 update
            user = userOptional.get();
            user.setEmail(oAuth2UserInfo.getEmail());
            user.setName(oAuth2UserInfo.getName());
        } else {
            user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .role("ROLE_USER")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .image(oAuth2UserInfo.getImage())
                    .locale(oAuth2UserInfo.locale())
                    .build();
        }
        log.info("user : {}", user);
        //  To Do :  email로 이미 있는 유저인지 검색 & DB에 저장
       registerUser(user);
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    public UserRequest registerUser(User user) {
        Optional<User> enterUser = userRepo.findByEmail(user.getEmail());
        //DB에 저장
        if(enterUser.isEmpty()){
            userRepo.save(user);
        } else {
            User existingUser = enterUser.get();
            log.info("이미 존재하는 유저입니다. {}",existingUser);
        }
        //controller에서 필요한 것은 이름,이메일,이미지가 있는 userRequest형식이므로 객체 만들어서 return해줌
        return UserRequest.builder()
                .name(user.getName())
                .email(user.getEmail())
                .image(user.getImage())
//                .role(user.getRole())
                .build();
    }

}
