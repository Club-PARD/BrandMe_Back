package com.soim.brandme.brandStory.service;

import com.soim.brandme.brandStory.dto.BrandStoryDto;
import com.soim.brandme.brandStory.entity.BrandStory;
import com.soim.brandme.brandStory.repo.BrandStoryRepo;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.chatRoom.repo.ChatRoomRepo;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandStoryService {
    private final BrandStoryRepo brandStoryRepo;
    private final UserRepo userRepo;
    private final ChatRoomRepo chatRoomRepo;

    public BrandStoryDto addBrandStory(Long userId, Long chatRoomId, BrandStoryDto brandStoryDto) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            Optional<ChatRoom> chatRoomOptional = u.getChatRooms().stream()
                    .filter(chatRoom -> chatRoom.getChatRoomId().equals(chatRoomId))
                    .findFirst();
            if (chatRoomOptional.isPresent()) {
                ChatRoom chatRoom = chatRoomOptional.get();

                // 이 부분은 BrandStory가 ChatRoom에 이미 있는지 확인하고, 없으면 새로 생성
                BrandStory brandStory = chatRoom.getBrandStory();
                if (brandStory == null) {
                    brandStory = BrandStory.builder()
                            .chatRoom(chatRoom)
                            .resources(brandStoryDto.getResources())
                            .slogan(brandStoryDto.getSlogan())
                            .suggestions(brandStoryDto.getSuggestions())
                            .niches(brandStoryDto.getNiches())
                            .build();
                    chatRoom.setBrandStory(brandStory); // ChatRoom에 BrandStory 설정
                } else {
                    // BrandStory가 이미 존재한다면, 내용을 업데이트합니다.
                    brandStory.setResources(brandStoryDto.getResources());
                    brandStory.setSlogan(brandStoryDto.getSlogan());
                    brandStory.setSuggestions(brandStoryDto.getSuggestions());
                    brandStory.setNiches(brandStoryDto.getNiches());
                }

                brandStoryRepo.save(brandStory); // 엔티티 저장

                return BrandStoryDto.builder()
                        .resources(brandStory.getResources())
                        .slogan(brandStory.getSlogan())
                        .suggestions(brandStory.getSuggestions())
                        .niches(brandStory.getNiches())
                        .build();
            } else {
                throw new IllegalArgumentException("해당 chatRoom id로 등록된 ChatRoom이 없습니다");
            }
        } else {
            throw new IllegalArgumentException("해당 user id로 등록된 User가 없습니다");
        }
    }
}
