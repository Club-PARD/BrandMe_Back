package com.soim.brandme.brandStory.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BrandStoryDto {
    List<String> brandKeywords;
    List<String> storyTitles;
    List<String> storyTexts;
    String resources;
    String target;
    String suggestions;
}
