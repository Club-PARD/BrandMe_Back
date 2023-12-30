package com.soim.brandme.user.dto.response;

import com.soim.brandme.chatRoom.entity.ChatRoom;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllResultResponse {
    Long userId;
    String name;
    String email;
    List<ChatRoom> chatRooms;
    String nickname;
}
