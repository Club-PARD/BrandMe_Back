package com.soim.brandme.chatRoom.presentation;

import com.soim.brandme.chatRoom.application.ChatRoomService;
import com.soim.brandme.chatRoom.presentation.request.ResultResponse;
import com.soim.brandme.user.application.UserService;
import com.soim.brandme.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@CrossOrigin(origins = "/**", allowedHeaders = "*")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @PostMapping( "/{userId}/chatRoom")
    public Long createChatRoom(@PathVariable Long userId){
        return chatRoomService.createChatRoom(userId);
    }
//    @PostMapping("/{userId}/{chatRoomId}")
//    public String saveWai(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody String wai){
//        return chatRoomService.saveWai(userId,chatRoomId,wai);
//    }

    @GetMapping("/{userId}/{chatRoomId}/myResult")
    public ResponseEntity<ResultResponse> getMyResult(@PathVariable Long userId, @PathVariable Long chatRoomId){
       ResultResponse result = chatRoomService.getMyResult(userId,chatRoomId);
         return ResponseEntity.ok(result);
    }
}
