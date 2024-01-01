package com.soim.brandme.chatRoom.repo;

import com.soim.brandme.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long>{
}
