package com.soim.brandme.controller;

import com.soim.brandme.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
