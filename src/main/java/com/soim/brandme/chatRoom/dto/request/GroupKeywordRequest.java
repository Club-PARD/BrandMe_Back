package com.soim.brandme.chatRoom.dto.request;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupKeywordRequest {
    private Map<String,List<String>> groupKeywords;
}
