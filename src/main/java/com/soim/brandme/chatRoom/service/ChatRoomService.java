package com.soim.brandme.chatRoom.service;

import com.soim.brandme.brandCard.dto.CardDtoForEntity;
import com.soim.brandme.brandCard.entity.BrandCard;
import com.soim.brandme.brandStory.dto.StoryDtoForEntity;
import com.soim.brandme.brandStory.entity.BrandStory;
import com.soim.brandme.chatRoom.dto.request.ChatRoomDto;
import com.soim.brandme.chatRoom.dto.request.GroupKeywordRequest;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.chatRoom.entity.GroupKeyword;
import com.soim.brandme.chatRoom.repo.ChatRoomRepo;
import com.soim.brandme.chatRoom.dto.response.ResultResponse;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class ChatRoomService {
    private final ChatRoomRepo chatRoomRepo;
    private final UserRepo userRepo;

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
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다"));

        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다"));

        if(!chatRoom.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("유저와 채팅방이 일치하지 않습니다");
        }
            return ResultResponse.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .chatNickName(chatRoom.getChatNickName())
                    .keywords(chatRoom.getKeywords())
                    .answers(chatRoom.getAnswers())
                    .brandCard(chatRoom.getBrandCard())
                    .brandStory(chatRoom.getBrandStory())
                    .build();

    }
//    public List<GroupKeywordRequest> saveGroupKeywords(Long userId, Long chatRoomId, List<GroupKeywordRequest> groupKeywords) {
//        Optional<User> user = userRepo.findById(userId);
//        if (user.isPresent()) {
//            User u = user.get();
//            ChatRoom chatRoom = u.getChatRooms().stream().filter(cr -> cr.getChatRoomId().equals(chatRoomId)).findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("해당 채팅방이 없습니다"));
//
//            List<GroupKeyword> g = groupKeywords.stream().map(gk -> GroupKeyword.builder()
//                    .id(gk.getId())
//                    .keywordValues(gk.getKeywordValues())
//                    .chatRoom(chatRoom)
//                    .build()).toList();
//            chatRoom.setGroupKeywords(g);
//            log.info("group Keywords"+ g.toString() + "chatRoom ==== {}", chatRoom.getChatRoomId());
//            chatRoomRepo.save(chatRoom);
//            return chatRoom.getGroupKeywords().stream().map((groupKeyword -> GroupKeywordRequest.builder()
//                    .id(groupKeyword.getId())
//                    .keywordValues(groupKeyword.getKeywordValues())
//                    .build())).toList();
//        } else {
//            throw new IllegalArgumentException("해당 유저가 없습니다");
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
