package com.soim.brandme.chatRoom.presentation;

import com.soim.brandme.chatRoom.application.ChatRoomService;
import com.soim.brandme.chatRoom.presentation.request.ResultResponse;
import com.soim.brandme.user.application.UserService;
import com.soim.brandme.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/{userId}/{chatRoomId}/savewai")
    public String saveWai(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody String wai){
        return chatRoomService.saveWai(userId,chatRoomId,wai);
    }

    @GetMapping("/{userId}/{chatRoomId}/myResult")
    public ResponseEntity<ResultResponse> getMyResult(@PathVariable Long userId, @PathVariable Long chatRoomId){
       ResultResponse result = chatRoomService.getMyResult(userId,chatRoomId);
         return ResponseEntity.ok(result);
    }

    @PostMapping("/{userId}/{chatRoomId}/keywords")
    public String saveKeywords(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody List<String> keywords){
        return chatRoomService.saveKeywords(userId,chatRoomId,keywords);
    }

    @PostMapping("/{userId}/{chatRoomId}/answers")
    public List<String> saveAnswers(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody List<String> answers){
        return chatRoomService.saveAnswers(userId,chatRoomId,answers);
    }
    @GetMapping("/{userId}/{chatRoomId}/answers")
    public List<String> getAnswers(@PathVariable Long userId, @PathVariable Long chatRoomId){
        return chatRoomService.getAnswers(userId,chatRoomId);
    }
    @GetMapping("/{userId}/{chatRoomId}/wai")
    public String getWai(@PathVariable Long userId, @PathVariable Long chatRoomId){
        return chatRoomService.getWai(userId,chatRoomId);
    }
    @GetMapping("/{userId}/{chatRoomId}/keywords")
    public List<String> getKeywords(@PathVariable Long userId, @PathVariable Long chatRoomId){
        return chatRoomService.getKeywords(userId,chatRoomId);
    }
}
