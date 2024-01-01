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
                ChatRoom c = chatRoomOptional.get();

                BrandCard brandCard = BrandCard.builder()
                        .identity(brandCardDto.getIdentity())
                        .identity_explaination(brandCardDto.getIdentity_explaination())
                        .chatRoom(c) // BrandCard와 ChatRoom 연결
                        .build();
                brandCardRepo.save(brandCard); // BrandCard 저장
                c.setBrandCard(brandCard); // ChatRoom에 BrandCard 설정
                chatRoomRepo.save(c); // ChatRoom 업데이트

                return BrandCardDto.builder()
                        .identity(brandCard.getIdentity())
                        .identity_explaination(brandCard.getIdentity_explaination())
                        .build();
            } else {
                throw new IllegalArgumentException("해당하는 채팅방이 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당하는 사용자가 없습니다.");
        }
    }

}
