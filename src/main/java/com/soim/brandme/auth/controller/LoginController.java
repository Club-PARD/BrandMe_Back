package com.soim.brandme.auth.controller;

import com.soim.brandme.auth.service.LoginService;
import com.soim.brandme.auth.dto.resonse.LoginResponse;
import com.soim.brandme.user.service.UserService;
import com.soim.brandme.user.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@CrossOrigin(origins = "/**", allowedHeaders = "*")
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;

//    @PostMapping("/oauth2/code/{registrationId}")
//    public void socialLogin(@RequestBody String code, @PathVariable String registrationId) {
//        log.info("Received OAuth2 code for registrationId: {}", registrationId);
//        loginService.socialLogin(code);
//    }

//    @GetMapping("/google")
//    public RedirectView redirectToGoogleOAuth(){
//        log.info("google login접속");
//        return new RedirectView("http://Soim-env.eba-v9sk9m3i.ap-northeast-2.elasticbeanstalk.com/oauth2/authorization/google");
//    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> UserInfoFromFront(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.UserInfoFromFront(userRequest), HttpStatus.OK);
    }
}
