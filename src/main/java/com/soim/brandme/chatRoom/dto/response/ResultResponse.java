package com.soim.brandme.chatRoom.dto.response;

import com.soim.brandme.brandCard.entity.BrandCard;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultResponse {
    private Long chatRoomId;
    private String chatNickName;
    private List<String> keywords;
    private List<String> answers;
    private BrandCard brandCard;
}
