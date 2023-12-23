package com.soim.brandme.user;

import com.soim.brandme.auth.PrincipalDetails;
import com.soim.brandme.user.GoogleUserInfo;
import com.soim.brandme.user.OAuth2UserInfo;
import com.soim.brandme.user.User;
import com.soim.brandme.user.presentation.request.UserRequest;
import com.soim.brandme.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
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
                    .build();
        }
        log.info("user : {}", user);
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
    @Transactional
    public User saveUser(UserRequest userRequest) {
        User ret = userRepo.save(User.from(userRequest));
        return ret;
    }

}
