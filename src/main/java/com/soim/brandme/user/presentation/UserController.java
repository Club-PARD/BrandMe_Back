package com.soim.brandme.user.presentation;

import com.soim.brandme.user.Oauth2UserService;
import com.soim.brandme.user.User;
import com.soim.brandme.user.presentation.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @PostMapping("/user/login")
    public ResponseEntity<UserRequest> regusterUser(@RequestBody User user){
//        프런트에 돌려주는 것은 UserRequest객체와 http상태
        return new ResponseEntity(oauth2UserService.registerUser(user), HttpStatus.CREATED);
    }
}
