package com.soim.brandme.user.service;

import com.soim.brandme.auth.dto.resonse.LoginResponse;
import com.soim.brandme.brandCard.dto.CardDtoForEntity;
import com.soim.brandme.brandCard.entity.BrandCard;
import com.soim.brandme.brandStory.dto.StoryDtoForEntity;
import com.soim.brandme.brandStory.entity.BrandStory;
import com.soim.brandme.chatRoom.dto.request.ChatRoomDto;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.user.dto.response.AllResultResponse;
import com.soim.brandme.user.dto.response.NicknameResponse;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.dto.request.UserRequest;
import com.soim.brandme.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        if(u.isPresent()){ //login은 했는데
            User user = u.get();
            if((user.getUsername())!=null){ //nickname까지 있다?
                user.setFirstLogin(false); //첫 로그인이 아니다
                log.info("존재하는 user : " + user);
                return LoginResponse.builder()
                        .userId(user.getId())
                        .firstLogin(false)
                        .build();
            } //nickname이 없다면
            return LoginResponse.builder()
                    .userId(user.getId())
                    .firstLogin(true)
                    .build();
        } else { //login을 처음 한다면
            if((userRequest.getEmail()!=null) && (userRequest.getEmail().contains("@"))) {
                User user = User.builder()
                        .name(userRequest.getName())
                        .email(userRequest.getEmail())
                        .image(userRequest.getPicture())
                        .build();
                log.info("신규 유저 정보 nickname 없음 : " + user);
                userRepo.save(user);
                return LoginResponse.builder()
                        .userId(user.getId())
                        .firstLogin(true)
                        .build();
            } else {
                throw new UsernameNotFoundException("잘못된 Google email 형식입니다");
            }
        }
    }
    public String saveNickname(Long userId, String nickname){
        Optional<User> user = userRepo.findById(userId);
            User u = user.get();
            if(nickname!=null){
                u.setUsername(nickname);
                u.setFirstLogin(false);
                userRepo.save(u);
                return u.getUsername();
        } else {
            throw new UsernameNotFoundException("해당 userId로 등록된 계정이 없습니다");
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
    public Map<Long,List<String>> allMyAnswers(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            Map<Long, List<String>> answers = new LinkedHashMap<>();
            for (ChatRoom chatRoom : u.getChatRooms()) {
                List<String> chatRoomAnswers = answers.computeIfAbsent(chatRoom.getChatRoomId(), k -> new ArrayList<>());
                // 현재 채팅방의 답변을 리스트에 추가
                chatRoomAnswers.addAll(chatRoom.getAnswers());
            }
            return answers;
        } else {
            throw new UsernameNotFoundException("해당 userId로 등록된 계정이 없습니다");
        }
    }
    public AllResultResponse allMyResult(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            List<ChatRoomDto> chatRoomDtos = u.getChatRooms().stream()
                    .map(this::converToChatRoomDto)
                    .toList();
            return AllResultResponse.builder()
                    .userId(u.getId())
                    .name(u.getName())
                    .email(u.getEmail())
                    .nickname(u.getUsername())
                    .chatRooms(chatRoomDtos)
                    .build();
        } else {
            throw new UsernameNotFoundException("해당 userId로 등록된 계정이 없습니다");
        }
    }
    public ChatRoomDto converToChatRoomDto(ChatRoom chatRoom){
        return ChatRoomDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .userId(chatRoom.getUser() != null ? chatRoom.getUser().getId() : null)
                .chatNickName(chatRoom.getChatNickName())
                .keywords(new ArrayList<>(chatRoom.getKeywords()))
                .answers(new ArrayList<>(chatRoom.getAnswers()))
                .brandStory(convertToBrandStoryDto(chatRoom.getBrandStory()))
                .brandCard(convertToBrandCardDto(chatRoom.getBrandCard()))
                .build();
    }
    public StoryDtoForEntity convertToBrandStoryDto(BrandStory brandStory) {
        if (brandStory == null) return null;
        return StoryDtoForEntity.builder()
                .brandStoryId(brandStory.getBrandStoryId())
                .resources(new ArrayList<>(brandStory.getResources()))
                .slogan(brandStory.getSlogan())
                .suggestions(new ArrayList<>(brandStory.getSuggestions()))
                .niches(new ArrayList<>(brandStory.getNiches()))
                .build();
    }

    public CardDtoForEntity convertToBrandCardDto(BrandCard brandCard) {
        if (brandCard == null) return null;
        return CardDtoForEntity.builder()
                .brandCardId(brandCard.getBrandCardId())
                .brandJob(brandCard.getBrandJob())
                .jobDetail(brandCard.getJobDetail())
                .build();
    }
}
