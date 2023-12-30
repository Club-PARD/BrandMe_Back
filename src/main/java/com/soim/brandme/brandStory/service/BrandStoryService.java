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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandStoryService {
    private final BrandStoryRepo brandStoryRepo;
    private final UserRepo userRepo;
    private final ChatRoomRepo chatRoomRepo;

    public BrandStoryDto addBrandStory(Long userId, Long chatRoomId, BrandStoryDto brandStoryDto){
        Optional<User> user = userRepo.findById(userId);
        Optional<ChatRoom> chatRoom = chatRoomRepo.findById(chatRoomId);
        if(user.isPresent() && chatRoom.isPresent()){
            ChatRoom c = chatRoom.get();
            User u = user.get();
            c.setUser(u);
            BrandStory brandStory = BrandStory.builder()
                    .resources(brandStoryDto.getResources())
                    .slogan(brandStoryDto.getSlogan())
                    .suggestions(brandStoryDto.getSuggestions())
                    .niches(brandStoryDto.getNiches())
                    .build();
            brandStory.setChatRoom(c);
            brandStoryRepo.save(brandStory);
            return BrandStoryDto.builder()
                    .resources(brandStory.getResources())
                    .slogan(brandStory.getSlogan())
                    .suggestions(brandStory.getSuggestions())
                    .niches(brandStory.getNiches())
                    .build();

        } else {
            throw new IllegalArgumentException("해당 user id 또는 chatRoom id로 등록된 계정이 없습니다");
        }
    }
}
