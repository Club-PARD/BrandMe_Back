package com.soim.brandme.user.controller;

import com.soim.brandme.auth.service.LoginService;
import com.soim.brandme.auth.service.Oauth2UserService;
import com.soim.brandme.chatRoom.dto.request.ChatRoomDto;
import com.soim.brandme.chatRoom.service.ChatRoomService;
import com.soim.brandme.user.dto.request.NickDto;
import com.soim.brandme.user.dto.response.AllResultResponse;
import com.soim.brandme.user.dto.response.NicknameResponse;
import com.soim.brandme.user.service.UserService;
import com.soim.brandme.user.dto.request.UserRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@PropertySource("classpath:env.properties")
@CrossOrigin(origins = "/**")
@RequestMapping("/user")
public class UserController {
    private final Oauth2UserService oauth2UserService;
    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final LoginService loginService;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;
//    @PostMapping("/user/login")
//    public ResponseEntity<UserRequest> regusterUser(@RequestBody User user){
////        프런트에 돌려주는 것은 UserRequest객체와 http상태
//        return new ResponseEntity(oauth2UserService.registerUser(user), HttpStatus.CREATED);
//    }
    @PatchMapping("/{userId}/updateProfile")
    public ResponseEntity<UserRequest> updateProfile(@RequestBody UserRequest userRequest, @PathVariable Long userId){
        UserRequest user = userService.updateProfile(userRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/{userId}/myProfile")
    public ResponseEntity<UserRequest> getMyProfile(@PathVariable Long userId){
        UserRequest user = userService.myProfile(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{userId}/allMyAnswers")
    public ResponseEntity<Map<Long, List<String>>> allUserAnswers(@PathVariable Long userId){
        Map<Long,List<String>> answers = userService.allMyAnswers(userId);
        return new ResponseEntity<>(answers,HttpStatus.OK);
    }
    @PostMapping("/{userId}/saveNickname")
    public NickDto saveNickname(@PathVariable Long userId, @RequestBody NickDto nickname){
        NickDto ret = userService.saveNickname(userId,nickname);
        return ret;
    }
    @GetMapping("/{userId}/allResults")
    public ResponseEntity<AllResultResponse> allResults(@PathVariable Long userId){
        AllResultResponse ret = userService.allMyResult(userId);
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }
    @GetMapping("/{userId}/recentChatRoom")
    public ChatRoomDto recentChatRoom(@PathVariable Long userId){
        ChatRoomDto ret = chatRoomService.recentChatRoom(userId);
        return ret;
    }


//    프런트에서 axios.get으로 유저 구글로그인+ 유저 정보 요청하면 내가 redirect url로 이동시켜주고 거기서 유저 정보를 받아서 프런트에 보내줌
//    @GetMapping("/googleLogin")
//    public ResponseEntity<UserRequest> userGoogleLoginThenUserInfoReturn(HttpServletResponse response) throws IOException {
//        // 유저를 oauth url로 이동시키는 메서드 만ㅍ
//        String googleOauthUrl = "https://accounts.google.com/o/oauth2/auth?client_id=googleClientId&redirect_uri=googleRedirectUri&response_type=code&scope=https://www.googleapis.com/auth/userinfo.emailhttps://www.googleapis.com/auth/userinfo.profile";
//        response.sendRedirect(googleOauthUrl);
//        //끝나면 값 다시 프런트로 돌려줌 -> Oauth2UserService에서 returnUserInfoToFront() 호출
//    }
//
}
