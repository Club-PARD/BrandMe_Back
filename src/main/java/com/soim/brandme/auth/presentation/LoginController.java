package com.soim.brandme.auth.presentation;

import com.soim.brandme.auth.application.LoginService;
import com.soim.brandme.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;

    @GetMapping("/oauth2/code/{registrationId}")
    public void socialLogin(@RequestParam("code") String code, @PathVariable String registrationId) {
        log.info("Received OAuth2 code for registrationId: {}", registrationId);
        loginService.socialLogin(code);
    }

    @GetMapping("/google")
    public RedirectView redirectToGoogleOAuth(){
        log.info("google login접속");
        return new RedirectView("/oauth2/authorization/google");

    }
}
