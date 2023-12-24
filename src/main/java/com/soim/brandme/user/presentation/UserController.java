package com.soim.brandme.user.presentation;

import com.soim.brandme.auth.application.Oauth2UserService;
import com.soim.brandme.user.domain.User;
import com.soim.brandme.user.application.UserService;
import com.soim.brandme.user.presentation.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {
    private final Oauth2UserService oauth2UserService;
    private final UserService userService;

    @PostMapping("/user/login")
    public ResponseEntity<UserRequest> regusterUser(@RequestBody User user){
//        프런트에 돌려주는 것은 UserRequest객체와 http상태
        return new ResponseEntity(oauth2UserService.registerUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserRequest> getUser(@PathVariable String email){
        return ResponseEntity.ok(userService.getUser(email));
    }
}
