package com.soim.brandme.user.application;

import com.soim.brandme.user.domain.User;
import com.soim.brandme.user.presentation.request.UserRequest;
import com.soim.brandme.user.domain.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public UserRequest getUser(String email){
        Optional<User> user = userRepo.findByEmail(email);
        if(user.isPresent()){
            User u = user.get();
            return UserRequest.builder()
                    .name(u.getName())
                    .email(u.getEmail())
                    .image(u.getImage())
                    .build();
        } else {
            throw new UsernameNotFoundException("해당 Google email로 등록된 계정이 없습니다" + email);
        }
    }
}
