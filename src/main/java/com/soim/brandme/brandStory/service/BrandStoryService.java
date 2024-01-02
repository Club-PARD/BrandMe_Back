package com.soim.brandme.brandStory.service;

import com.soim.brandme.brandStory.dto.BrandStoryResponse;
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
    private final ChatRoomRepo chatRoomRepo;
    private final UserRepo userRepo;

    public BrandStoryResponse createBrandStory(Long userId, Long chatRoomId, BrandStoryResponse brandStoryResponse) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        User user = userOpt.get();

        Optional<ChatRoom> chatRoomOpt = chatRoomRepo.findById(chatRoomId);
        if (chatRoomOpt.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }
        ChatRoom chatRoom = chatRoomOpt.get();
       BrandStory brandStory = chatRoom.getBrandStory();
//        BrandStory brandStory = brandStoryRepo.findByChatRoomId(chatRoomId);
        if (brandStory == null) {
            // BrandStory가 없는 경우, 새로 생성
            brandStory = BrandStory.builder()
                    .chatRoom(chatRoom)
                    .identity(brandStoryResponse.getIdentity())
                    .identity_explanation(brandStoryResponse.getIdentityExplanation())
                    .competency(brandStoryResponse.getCompetency())
                    .target(brandStoryResponse.getTarget())
                    .contentsRecommendation(brandStoryResponse.getContentsRecommendation())
                    .brandKeywords(brandStoryResponse.getBrandKeywords())
                    .storyHeadlines(brandStoryResponse.getStoryHeadlines())
                    .storyContents(brandStoryResponse.getStoryContents())
                    .build();
        } else {
            // BrandStory가 이미 있는 경우, 업데이트
            brandStory.setIdentity(brandStoryResponse.getIdentity());
            brandStory.setIdentity_explanation(brandStoryResponse.getIdentityExplanation());
            brandStory.setCompetency(brandStoryResponse.getCompetency());
            brandStory.setTarget(brandStoryResponse.getTarget());
            brandStory.setContentsRecommendation(brandStoryResponse.getContentsRecommendation());
            brandStory.setBrandKeywords(brandStoryResponse.getBrandKeywords());
            brandStory.setStoryHeadlines(brandStoryResponse.getStoryHeadlines());
            brandStory.setStoryContents(brandStoryResponse.getStoryContents());
        }

        brandStoryRepo.save(brandStory); // BrandStory 저장 또는 업데이트

        // DTO로 변환하여 반환
        return BrandStoryResponse.builder()
                .identity(brandStory.getIdentity())
                .identityExplanation(brandStory.getIdentity_explanation())
                .competency(brandStory.getCompetency())
                .target(brandStory.getTarget())
                .contentsRecommendation(brandStory.getContentsRecommendation())
                .brandKeywords(brandStory.getBrandKeywords())
                .storyHeadlines(brandStory.getStoryHeadlines())
                .storyContents(brandStory.getStoryContents())
                .build();
    }

}
