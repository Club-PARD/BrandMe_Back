package com.soim.brandme.brandCard.service;

import com.soim.brandme.brandCard.dto.BrandCardDto;
import com.soim.brandme.brandCard.entity.BrandCard;
import com.soim.brandme.brandCard.repo.BrandCardRepo;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.chatRoom.repo.ChatRoomRepo;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class BrandCardService {
    private final BrandCardRepo brandCardRepo;
    private final UserRepo userRepo;
    private final ChatRoomRepo chatRoomRepo;


    public BrandCardDto addBrandCard(Long userId, Long chatRoomId, BrandCardDto brandCardDto) {
        Optional<User> user = userRepo.findById(userId);
        Optional<ChatRoom> chatRoom = chatRoomRepo.findById(chatRoomId);

        if (user.isPresent() && chatRoom.isPresent()) {
            ChatRoom c = chatRoom.get();
            c.setUser(user.get());

            // BrandCard가 존재하는지 확인하고, 존재하지 않으면 새로 생성
             BrandCard b = brandCardRepo.findById(chatRoomId)
                    .orElse(BrandCard.builder()
                            .brandJob(brandCardDto.getBrandJob())
                            .jobDetail(brandCardDto.getJobDetail())
                            .build());

            // 기존 또는 새로운 BrandCard 객체 업데이트
            b.setBrandJob(brandCardDto.getBrandJob());
            b.setJobDetail(brandCardDto.getJobDetail());
            b.setChatRoom(c);

            // BrandCard 저장
            brandCardRepo.save(b);

            return BrandCardDto.builder()
                    .brandJob(b.getBrandJob())
                    .jobDetail(b.getJobDetail())
                    .build();
        } else {
            throw new IllegalArgumentException("해당 user id 또는 chatRoom id로 등록된 계정이 없습니다");
        }
    }

}
