package com.soim.brandme.chatRoom.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupKeywordRequest {
    private String key;
    private List<String> keywordValues;
}
