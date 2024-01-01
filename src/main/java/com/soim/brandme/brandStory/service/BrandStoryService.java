package com.soim.brandme.brandStory.service;

import com.soim.brandme.brandStory.dto.BrandStoryDto;
import com.soim.brandme.brandStory.entity.BrandStory;
import com.soim.brandme.brandStory.repo.BrandStoryRepo;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.chatRoom.repo.ChatRoomRepo;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandStoryService {
    private final BrandStoryRepo brandStoryRepo;
    private final UserRepo userRepo;
    private final ChatRoomRepo chatRoomRepo;

    public BrandStoryDto saveBrandStory(Long userId, Long chatRoomId, BrandStoryDto brandStoryDto){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            Optional<ChatRoom> chatRoom = chatRoomRepo.findById(chatRoomId);
            if(chatRoom.isPresent()){
                ChatRoom ch = chatRoom.get();
                ch.setUser(user.get());
                BrandStory brandStory = BrandStory.builder()
                        .brandKeywords(brandStoryDto.getBrandKeywords())
                        .brandKeywords(brandStoryDto.getBrandKeywords())
                        .storyTexts(brandStoryDto.getStoryTexts())
                        .storyTitles(brandStoryDto.getStoryTitles())
                        .resources(brandStoryDto.getResources())
                        .suggestions(brandStoryDto.getSuggestions())
                        .target(brandStoryDto.getTarget())
                        .chatRoom(ch)
                        .build();
                brandStoryRepo.save(brandStory);
                ch.setBrandStory(brandStory);
                return BrandStoryDto.builder()
                        .brandKeywords(brandStory.getBrandKeywords())
                        .brandKeywords(brandStory.getBrandKeywords())
                        .storyTexts(brandStory.getStoryTexts())
                        .storyTitles(brandStory.getStoryTitles())
                        .resources(brandStory.getResources())
                        .suggestions(brandStory.getSuggestions())
                        .target(brandStory.getTarget())
                        .build();
            }
        } else{
            throw new IllegalArgumentException("해당 user id 또는 chatRoom id로 등록된 계정이 없습니다");
        }
        return brandStoryDto;
    }
}
