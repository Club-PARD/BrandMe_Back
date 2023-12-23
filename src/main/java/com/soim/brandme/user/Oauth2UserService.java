package com.soim.brandme.user;

import com.soim.brandme.auth.PrincipalDetails;
import com.soim.brandme.user.GoogleUserInfo;
import com.soim.brandme.user.OAuth2UserInfo;
import com.soim.brandme.user.User;
import com.soim.brandme.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
//        Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        Optional<User> userOptional = userRepo.findByEmail(oAuth2UserInfo.getEmail());

        User user;
        if(userOptional.isPresent()) {
//            user정보 존재하면 update
            user = userOptional.get();
            user.setEmail(oAuth2UserInfo.getEmail());
            user.setName(oAuth2UserInfo.getName());
        } else {
            user = User.builder()
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .role(Role.valueOf("USER"))
                    .build();
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }


}
