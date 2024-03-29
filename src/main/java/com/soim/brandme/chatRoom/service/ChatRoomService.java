package com.soim.brandme.chatRoom.service;

import com.soim.brandme.chatRoom.dto.request.ChatNickDto;
import com.soim.brandme.chatRoom.dto.request.ChatRoomDto;
import com.soim.brandme.chatRoom.dto.request.DraftDto;
import com.soim.brandme.chatRoom.dto.request.GroupKeywordRequest;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.chatRoom.entity.EmbedGroupKeyword;
import com.soim.brandme.chatRoom.repo.ChatRoomRepo;
import com.soim.brandme.chatRoom.dto.response.ResultResponse;
import com.soim.brandme.exception.BrandOnException;
import com.soim.brandme.exception.ErrorCode;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class ChatRoomService {
    private final ChatRoomRepo chatRoomRepo;
    private final UserRepo userRepo;

//    public ChatRoomDto converToChatRoomDto(ChatRoom chatRoom){
//        return ChatRoomDto.builder()
//                .chatRoomId(chatRoom.getChatRoomId())
////                .userId(chatRoom.getUser() != null ? chatRoom.getUser().getId() : null)
//                .chatNickName(chatRoom.getChatNickName())
//                .keywords(new ArrayList<>(chatRoom.getKeywords()))
//                .answers(new ArrayList<>(chatRoom.getAnswers()))
//                .finishChat(chatRoom.isFinishChat())
//                .brandStory(convertToBrandStoryDto(chatRoom.getBrandStory()))
//                .brandCard(convertToBrandCardDto(chatRoom.getBrandCard()))
//                .build();
//    }
//    public StoryDtoForEntity convertToBrandStoryDto(BrandStory brandStory) {
//        if (brandStory == null) return null;
//        return StoryDtoForEntity.builder()
//                .brandStoryId(brandStory.getBrandStoryId())
//                .resources(brandStory.getResources())
//                .storyText(brandStory.getStoryText())
//                .storyTitle(brandStory.getStoryTitle())
//                .target(brandStory.getTarget())
//                .suggestions(brandStory.getSuggestions())
//                .brandKeywords(brandStory.getBrandKeywords())
//                .build();
//    }
//
//    public CardDtoForEntity convertToBrandCardDto(BrandCard brandCard) {
//        if (brandCard == null) return null;
//        return CardDtoForEntity.builder()
//                .brandCardId(brandCard.getBrandCardId())
//                .brandJob(brandCard.getBrandJob())
//                .jobDetail(brandCard.getJobDetail())
//                .build();
//    }
    public ChatRoomDto recentChatRoom(Long userId){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User u = user.get();
            List<ChatRoom> chatRooms = u.getChatRooms();
            ChatRoom ch = chatRooms.get(chatRooms.size()-1);
            ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                    .chatRoomId(ch.getChatRoomId())
                    .progress(ch.getProgress())
                    .finishChat(ch.isFinishChat())
                    .chatNickName(ch.getChatNickName())
                    .keywords(ch.getKeywords())
                    .answers(ch.getAnswers())
                    .build();
            return chatRoomDto;
        } else {
            throw new BrandOnException(ErrorCode.USERID_NOT_FOUND);
        }
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
    public ChatNickDto saveChatNickName(Long userId, Long chatRoomId, ChatNickDto chatNickName){
        User user = userRepo.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다"));

        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방이 없습니다"));
            chatRoom.setUser(user);
            chatRoom.setChatNickName(chatNickName.getChatNickName());
            chatRoom = chatRoomRepo.save(chatRoom);
            ChatNickDto chatNickDto = ChatNickDto.builder()
                    .chatNickName(chatRoom.getChatNickName())
                    .build();
            return chatNickDto;
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

    public ChatRoomDto getMyResult(Long userId, Long chatRoomId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다"));

        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다"));

        if(!chatRoom.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("유저와 채팅방이 일치하지 않습니다");
        }
            return ChatRoomDto.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .chatNickName(chatRoom.getChatNickName())
                    .keywords(chatRoom.getKeywords())
                    .answers(chatRoom.getAnswers())
                    .brandCard(chatRoom.getBrandCard())
                    .finishChat(chatRoom.isFinishChat())
                    .brandStory(chatRoom.getBrandStory())
                    .progress(chatRoom.getProgress())
                    .build();

    }

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


    public DraftDto saveDraftAnswers(Long userId, Long chatRoomId, DraftDto draftDto) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 없습니다");
        }

        // 채팅방 찾기
        Optional<ChatRoom> chatRoomOpt = chatRoomRepo.findById(chatRoomId);
        if (chatRoomOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 채팅방이 없습니다");
        }

        // 기존 답변에 새로운 답변 추가
        ChatRoom chatRoom = chatRoomOpt.get();
        List<String> existingAnswers = new ArrayList<>();
        if (chatRoom.getAnswers() != null) {
            existingAnswers.addAll(chatRoom.getAnswers());
        }

        // 새로운 답변 추가
        existingAnswers.addAll(draftDto.getAnswers());
        chatRoom.setProgress(draftDto.getProgress());
        chatRoom.setAnswers(existingAnswers);

        // 변경 사항 저장
        chatRoomRepo.save(chatRoom);

        // 업데이트된 답변 리스트 반환
        return DraftDto.builder()
                .progress(chatRoom.getProgress())
                .answers(chatRoom.getAnswers())
                .build();
    }
    @Transactional
    public boolean finishChat(Long userId, Long chatRoomId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException("해당 유저가 없습니다");
        } else {
            User u = user.get();
            ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).orElseThrow(() ->
                    new IllegalArgumentException("해당 채팅방이 없습니다"));
            chatRoom.setFinishChat(true);
            chatRoomRepo.save(chatRoom);
            return chatRoom.isFinishChat();
        }
    }

    public GroupKeywordRequest saveGroupKeywords(Long userId, Long chatRoomId, Map<String,List<String>> groupKeywords) {


        ChatRoom chatRoom = chatRoomRepo.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방이 없습니다"));

        // Map<String, List<String>>를 Map<String, EmbedGroupKeyword>로 변환
        Map<String, EmbedGroupKeyword> convertedGroupKeywords = new HashMap<>();
        groupKeywords.forEach((key, valueList) -> {
            EmbedGroupKeyword embedGroupKeyword = new EmbedGroupKeyword(valueList);
            convertedGroupKeywords.put(key, embedGroupKeyword);
        });

        chatRoom.setGroupKeywords(convertedGroupKeywords);
        chatRoomRepo.save(chatRoom);

        return GroupKeywordRequest.builder()
                .groupKeywords(groupKeywords)
                .build();
    }
}
