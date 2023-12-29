package com.soim.brandme.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnswerResponse {
    private Long chatRoomId;
    List<String> answers;
}
