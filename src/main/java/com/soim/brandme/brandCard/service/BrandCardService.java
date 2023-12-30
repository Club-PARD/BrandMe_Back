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


    public BrandCardDto addBrandCard(Long userId, Long chatRoomId, BrandCardDto brandCard) {
        Optional<User> user = userRepo.findById(userId);
        Optional<ChatRoom> chatRoom = chatRoomRepo.findById(chatRoomId);
        if(user.isPresent() && chatRoom.isPresent()){
            ChatRoom c = chatRoom.get();
            c.setUser(user.get());
            BrandCard b = BrandCard.builder()
                    .brandJob(brandCard.getBrandJob())
                    .jobDetails(brandCard.getJobDetails())
                    .build();
            b.setChatRoom(c);
            brandCardRepo.save(b);
            return BrandCardDto.builder()
                    .brandJob(b.getBrandJob())
                    .jobDetails(b.getJobDetails())
                    .build();
        } else throw new IllegalArgumentException("해당 user id 또는 chatRoom id로 등록된 계정이 없습니다");
    }
}
