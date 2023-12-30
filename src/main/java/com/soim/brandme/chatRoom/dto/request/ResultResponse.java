package com.soim.brandme.chatRoom.dto.request;

import com.soim.brandme.brandCard.entity.BrandCard;
import com.soim.brandme.brandStory.entity.BrandStory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultResponse {
    private Long chatRoomId;
    private String wai;
    private List<String> keywords;
    private List<String> answers;
    private BrandCard brandCard;
    private BrandStory brandStory;
}
