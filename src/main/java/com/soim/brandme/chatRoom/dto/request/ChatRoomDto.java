package com.soim.brandme.chatRoom.dto.request;

import com.soim.brandme.brandCard.entity.BrandCard;
import com.soim.brandme.brandStory.entity.BrandStory;
import com.soim.brandme.chatRoom.entity.EmbedGroupKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    Long chatRoomId;
//    Long userId;
    int progress;
    boolean finishChat;
    private String chatNickName;
    private List<String> keywords;
    private List<String> answers;
    Map<String, EmbedGroupKeyword> groupKeywords;
     BrandCard brandCard;
    BrandStory brandStory;
}
