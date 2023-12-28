package com.soim.brandme.chatRoom.application;

import com.soim.brandme.chatRoom.domain.ChatRoom;
import com.soim.brandme.chatRoom.domain.repo.ChatRoomRepo;
import com.soim.brandme.chatRoom.presentation.request.ResultResponse;
import com.soim.brandme.user.domain.User;
import com.soim.brandme.user.domain.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public String saveWai(Long userId,Long chatRoomId,String wai){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User u = user.get();

            ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).get();
            chatRoom.setUser(u);
            chatRoom = chatRoomRepo.save(chatRoom);
            return chatRoom.getWai();
        } else {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }
    }

    public ResultResponse getMyResult(Long userId, Long chatRoomId){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User u = user.get();
            ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).get();
            return ResultResponse.builder()
//                    .chatRoomId(chatRoom.getChatRoomId())
                    .wai(chatRoom.getWai())
                    .build();
        } else {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }
    }
}
