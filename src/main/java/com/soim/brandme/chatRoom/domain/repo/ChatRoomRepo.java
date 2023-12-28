package com.soim.brandme.chatRoom.domain.repo;

import com.soim.brandme.chatRoom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long>{
}
