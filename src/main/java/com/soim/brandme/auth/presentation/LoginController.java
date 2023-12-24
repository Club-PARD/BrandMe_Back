package com.soim.brandme.auth.presentation;

import com.soim.brandme.auth.application.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/code/{registrationId}")
    public void socialLogin(@RequestBody String code, @PathVariable String registrationId) {
        log.info(code);
        loginService.socialLogin(code, registrationId);
    }

}
