package com.soim.brandme.chatRoom.presentation.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultResponse {
    private Long chatRoomId;
    private String wai;
//    private List<String> keywords;
}
