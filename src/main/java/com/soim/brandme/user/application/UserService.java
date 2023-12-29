package com.soim.brandme.user.application;

import com.soim.brandme.auth.presentation.resonse.LoginResponse;
import com.soim.brandme.chatRoom.domain.ChatRoom;
import com.soim.brandme.user.domain.User;
import com.soim.brandme.user.presentation.request.UserRequest;
import com.soim.brandme.user.domain.repo.UserRepo;
import com.soim.brandme.user.presentation.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
        Optional<User> user = userRepo.findByEmail(userRequest.getEmail());
        if (user.isPresent()) {
            User u = user.get();
            u.setName(userRequest.getName());
            u.setEmail(userRequest.getEmail());
            userRepo.save(u);
            UserRequest req =UserRequest.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .build();
            return req;
        } else {
            throw new UsernameNotFoundException("해당 Google email로 등록된 계정이 없습니다");
        }
    }

    public LoginResponse UserInfoFromFront(UserRequest userRequest){
        Optional<User> u = userRepo.findByEmail(userRequest.getEmail());
        if(u.isPresent()){
            User user = u.get();
            user.setFirstLogin(true);
            log.info("존재하는 user : " + user);
            return LoginResponse.builder()
                    .userId(user.getId())
                    .firstLogin(true)
                    .build();
        } else {
            if((userRequest.getEmail()!=null) && (userRequest.getEmail().contains("@gmail.com"))) {
                User user = User.builder()
                        .name(userRequest.getName())
                        .email(userRequest.getEmail())
                        .image(userRequest.getPicture())
                        .firstLogin(true)
                        .build();
                log.info("신규 유저 정보 : " + user);
                userRepo.save(user);
                return LoginResponse.builder()
                        .userId(user.getId())
                        .firstLogin(false)
                        .build();
            } else {
                throw new UsernameNotFoundException("잘못된 Google email 형식입니다");
            }
        }
    }
    public UserRequest myProfile(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            return UserRequest.builder()
                    .name(u.getName())
                    .email(u.getEmail())
                    .build();
        } else {
            throw new UsernameNotFoundException("해당 userId로 등록된 계정이 없습니다");
        }
    }
    public List<String> allMyAnswers(Long id){
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
           User u = user.get();
           List<String> answers = new ArrayList<>();
           for(ChatRoom chatRoom : u.getChatRooms()){
               answers.addAll(chatRoom.getAnswers());
           }
           return answers;
        } else {
            throw new UsernameNotFoundException("해당 userId로 등록된 계정이 없습니다");
        }
    }
}
