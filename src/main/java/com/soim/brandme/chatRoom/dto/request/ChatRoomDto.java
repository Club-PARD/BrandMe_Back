package com.soim.brandme.chatRoom.dto.request;

import com.soim.brandme.brandCard.dto.CardDtoForEntity;
import com.soim.brandme.brandStory.dto.BrandStoryDto;
import com.soim.brandme.brandStory.dto.StoryDtoForEntity;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    Long chatRoomId;
    Long userId;
    private String chatNickName;
    private List<String> keywords;
    private List<String> answers;
    private StoryDtoForEntity brandStory;
    private CardDtoForEntity brandCard;
}
