package com.soim.brandme.brandCard.service;

import com.soim.brandme.brandCard.dto.BrandCardDto;
import com.soim.brandme.brandCard.entity.BrandCard;
import com.soim.brandme.brandCard.repo.BrandCardRepo;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.chatRoom.repo.ChatRoomRepo;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandCardService {
    private final BrandCardRepo brandCardRepo;
    private final UserRepo userRepo;
    private final ChatRoomRepo chatRoomRepo;

    public BrandCardDto saveBrandCard(Long userId, Long chatRoomId, BrandCardDto brandCardDto) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            Optional<ChatRoom> chatRoomOptional = u.getChatRooms().stream()
                    .filter(c -> c.getChatRoomId().equals(chatRoomId))
                    .findFirst();

            if (chatRoomOptional.isPresent()) {
                ChatRoom chatRoom = chatRoomOptional.get();
                BrandCard brandCard = chatRoom.getBrandCard(); // 기존 BrandCard 가져오기

                if (brandCard == null) {
                    // 기존 BrandCard가 없으면 새로 생성
                    brandCard = BrandCard.builder()
                            .identity(brandCardDto.getIdentity())
                            .identity_explanation(brandCardDto.getIdentity_explanation())
                            .chatRoom(chatRoom)
                            .build();
                } else {
                    // 기존 BrandCard가 있으면 업데이트
                    brandCard.setIdentity(brandCardDto.getIdentity());
                    brandCard.setIdentity_explanation(brandCardDto.getIdentity_explanation());
                    // 필요한 경우, 다른 필드도 업데이트
                }

                brandCardRepo.save(brandCard); // BrandCard 저장 또는 업데이트
                chatRoom.setBrandCard(brandCard); // ChatRoom에 BrandCard 설정
                chatRoomRepo.save(chatRoom); // ChatRoom 업데이트

                return BrandCardDto.builder()
                        .identity(brandCard.getIdentity())
                        .identity_explanation(brandCard.getIdentity_explanation())
                        .build();
            } else {
                throw new IllegalArgumentException("해당하는 채팅방이 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당하는 사용자가 없습니다.");
        }
    }


}
