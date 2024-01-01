package com.soim.brandme.user.dto.response;

import com.soim.brandme.chatRoom.dto.request.ChatRoomDto;
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
    String nickname;
    List<ChatRoomDto> chatRooms; //entity성의 가지고 있음 -> 순환참조하기 때문에 한번 더 dto로 매핑
}
