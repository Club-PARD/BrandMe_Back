package com.soim.brandme.brandCard.repo;

import com.soim.brandme.brandCard.entity.BrandCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BrandCardRepo extends JpaRepository<BrandCard, Long> {
//    Optional<BrandCard> findByChatRoomId(Long chatRoomId);
}
