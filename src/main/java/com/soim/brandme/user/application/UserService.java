package com.soim.brandme.user.application;

import com.soim.brandme.user.domain.User;
import com.soim.brandme.user.presentation.request.UserRequest;
import com.soim.brandme.user.domain.repo.UserRepo;
import com.soim.brandme.user.presentation.response.UserResponse;
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
                    .build();
        } else {
            throw new UsernameNotFoundException("해당 Google email로 등록된 계정이 없습니다" + email);
        }
    }

    public User findUserId(Long id){
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            User u = user.get();
            return u;
        } else {
            throw new UsernameNotFoundException("해당 user id로 등록된 계정이 없습니다");
        }
    }

    public UserRequest updateProfile(UserRequest userRequest) {
        String userEmail = userRequest.getEmail();
        Optional<User> user = userRepo.findByEmail(userEmail);
        if (user.isPresent()) {
            User u = user.get();
            u.update(userRequest.getName());
            userRepo.save(u);
            return UserRequest.builder()
                    .name(u.getName())
                    .email(u.getEmail())
                    .build();
        } else {
            throw new UsernameNotFoundException("해당 Google email로 등록된 계정이 없습니다" + userEmail);
        }
    }

    public Long UserInfoFromFront(UserRequest userRequest){
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .image(userRequest.getPicture())
                .build();
        Long id = userRepo.save(user).getId();
            return id;
    }


    public UserRequest myProfile(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();
            return UserRequest.builder()
                    .name(u.getName())
                    .email(u.getEmail())
                    .build();
        } else {
            throw new UsernameNotFoundException("해당 Google email로 등록된 계정이 없습니다" + email);
        }

    }
}
