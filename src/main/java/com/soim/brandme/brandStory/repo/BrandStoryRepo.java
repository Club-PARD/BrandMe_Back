package com.soim.brandme.brandStory.repo;

import com.soim.brandme.brandStory.entity.BrandStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandStoryRepo extends JpaRepository<BrandStory, Long> {
    @Query("select c from ChatRoom c where c.chatRoomId = :chatRoomId")
     BrandStory findByChatRoomId(Long chatRoomId);
}
