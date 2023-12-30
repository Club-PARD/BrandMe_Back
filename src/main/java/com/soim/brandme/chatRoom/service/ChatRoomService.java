package com.soim.brandme.chatRoom.service;

import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.chatRoom.repo.ChatRoomRepo;
import com.soim.brandme.chatRoom.dto.request.ResultResponse;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class ChatRoomService {
    private final ChatRoomRepo chatRoomRepo;
    private final UserRepo userRepo;

    public Long createChatRoom(Long userId){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User u = user.get();
            ChatRoom chatRoom = ChatRoom.builder()
                    .user(u)
                    .build();
            chatRoom = chatRoomRepo.save(chatRoom);
            return chatRoom.getChatRoomId();
        } else {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }
    }
    public String saveChatNickName(Long userId,Long chatRoomId,String chatNickName){
        User user = userRepo.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다"));

        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방이 없습니다"));
            chatRoom.setUser(user);
            chatRoom.setChatNickName(chatNickName);
            chatRoom = chatRoomRepo.save(chatRoom);
            return chatRoom.getChatNickName();
    }
    public String getChatNickName(Long userId,Long chatRoomId){
        User user = userRepo.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다"));

        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방이 없습니다"));
        return chatRoom.getChatNickName();
    }
    public String saveKeywords(Long userId, Long chatRoomId, List<String> keywords) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }

        Optional<ChatRoom> chatRoom = chatRoomRepo.findById(chatRoomId);
        if (chatRoom.isEmpty()) {
            throw new IllegalArgumentException("해당 채팅방이 없습니다");
        }

        ChatRoom c = chatRoom.get();
        c.setKeywords(keywords);
        chatRoomRepo.save(c);
        return "keywords 저장 완료";
    }
    public List<String> getKeywords(Long userId, Long chatRoomId){
        User user = userRepo.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다"));

        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방이 없습니다"));
        return chatRoom.getKeywords();
    }

    public ResultResponse getMyResult(Long userId, Long chatRoomId){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User u = user.get();
            ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).get();
            return ResultResponse.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .chatNickName(chatRoom.getChatNickName())
                    .keywords(chatRoom.getKeywords())
                    .answers(chatRoom.getAnswers())
                    .brandCard(chatRoom.getBrandCard())
                    .brandStory(chatRoom.getBrandStory())
                    .build();
        } else {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }
    }
//    public Map<Long,List<String>> saveGroupKeywords(Long userId,Long chatRoomId,Map<Long,List<String>> groupKeywords){
//        Optional<User> user = userRepo.findById(userId);
//        if(user.isPresent()){
//            User u = user.get();
//            ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).get();
//            chatRoom.setGroupKeywords(groupKeywords);
//        }
//    }
    public List<String> saveAnswers(Long userId, Long chatRoomId, List<String> answers) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }
        Optional<ChatRoom> chatRoom = chatRoomRepo.findById(chatRoomId);
        if (chatRoom.isEmpty()) {
            throw new IllegalArgumentException("해당 채팅방이 없습니다");
        }
        ChatRoom c = chatRoom.get();
        c.setAnswers(answers);
        chatRoomRepo.save(c);
        return c.getAnswers();
    }
    public List<String> getAnswers(Long userId, Long chatRoomId){
        User user = userRepo.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다"));

        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방이 없습니다"));
        return chatRoom.getAnswers();
    }
}
