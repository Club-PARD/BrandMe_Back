package com.soim.brandme.chatRoom.controller;

import com.soim.brandme.chatRoom.dto.request.DraftDto;
import com.soim.brandme.chatRoom.dto.request.GroupKeywordRequest;
import com.soim.brandme.chatRoom.entity.EmbedGroupKeyword;
import com.soim.brandme.chatRoom.service.ChatRoomService;
import com.soim.brandme.chatRoom.dto.response.ResultResponse;
import com.soim.brandme.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @PostMapping("/{userId}/{chatRoomId}/saveChatNickName")
    public String saveChatNickName(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody String chatNickName){
        return chatRoomService.saveChatNickName(userId,chatRoomId,chatNickName);
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
    @PostMapping("/{userId}/{chatRoomId}/draftAnswers")
    public ResponseEntity<DraftDto> saveDraftAnswers(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody DraftDto draft){
        DraftDto ret = chatRoomService.saveDraftAnswers(userId,chatRoomId,draft);
        return ResponseEntity.ok(ret);
    }

    @PostMapping("/{userId}/{chatRoomId}/answers")
    public ResponseEntity<List<String>> saveAnswers(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody List<String> answers){
        List<String> ret = chatRoomService.saveAnswers(userId,chatRoomId,answers);
        return ResponseEntity.ok(ret);
    }
    @GetMapping("/{userId}/{chatRoomId}/answers")
    public List<String> getAnswers(@PathVariable Long userId, @PathVariable Long chatRoomId){
        return chatRoomService.getAnswers(userId,chatRoomId);
    }
    @GetMapping("/{userId}/{chatRoomId}/chatNickName")
    public String getChatNickName(@PathVariable Long userId, @PathVariable Long chatRoomId){
        return chatRoomService.getChatNickName(userId,chatRoomId);
    }
    @GetMapping("/{userId}/{chatRoomId}/keywords")
    public List<String> getKeywords(@PathVariable Long userId, @PathVariable Long chatRoomId){
        return chatRoomService.getKeywords(userId,chatRoomId);
    }
    @GetMapping("/{userId}/{chatRoomId}/finishChat")
    public boolean finishChat(@PathVariable Long userId, @PathVariable Long chatRoomId){
        return chatRoomService.finishChat(userId,chatRoomId);
    }
    @PostMapping("/{userId}/{chatRoomId}/groupKeywords")
    public ResponseEntity<GroupKeywordRequest> saveGroupKeywords(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody GroupKeywordRequest groupKeywords){
        GroupKeywordRequest ret = chatRoomService.saveGroupKeywords(userId,chatRoomId,groupKeywords);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }


//    @PostMapping("/{userId}/{chatRoomId}/groupKeywords")
//    public ResponseEntity<List<GroupKeywordRequest>> saveGroupKeywords(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody List<GroupKeywordRequest> groupKeywords){
//        List<GroupKeywordRequest> ret = chatRoomService.saveGroupKeywords(userId,chatRoomId,groupKeywords);
//        return new ResponseEntity<>(ret, HttpStatus.OK);
//    }
}
