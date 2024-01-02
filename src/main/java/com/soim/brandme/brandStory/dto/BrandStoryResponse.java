package com.soim.brandme.brandStory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandStoryResponse {
    private String identity;
    private String identityExplanation;
    private String competency;
    private String target;
    private String contentsRecommendation;
    private List<String> brandKeywords;
    private List<String> storyHeadlines;
    private List<String> storyContents;
}
