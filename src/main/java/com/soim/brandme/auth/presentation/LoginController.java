package com.soim.brandme.auth.presentation;

import com.soim.brandme.auth.application.LoginService;
import com.soim.brandme.user.application.UserService;
import com.soim.brandme.user.presentation.request.UserRequest;
import com.soim.brandme.user.presentation.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@CrossOrigin(origins = "/**", allowedHeaders = "*")
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;

    @GetMapping("/oauth2/code/{registrationId}")
    public void socialLogin(@RequestBody String code, @PathVariable String registrationId) {
        log.info("Received OAuth2 code for registrationId: {}", registrationId);
        loginService.socialLogin(code);
    }

//    @GetMapping("/google")
//    public RedirectView redirectToGoogleOAuth(){
//        log.info("google login접속");
//        return new RedirectView("http://localhost:8080/oauth2/authorization/google");
//    }

    @PostMapping("/google")
    public ResponseEntity<Long> UserInfoFromFront(@RequestBody UserRequest userRequest){
        log.info("프런트에서 오는 유저 정보 : " + userRequest);
        return new ResponseEntity(userService.UserInfoFromFront(userRequest), HttpStatus.OK);
    }
}
