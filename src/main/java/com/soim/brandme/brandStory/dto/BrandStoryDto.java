package com.soim.brandme.brandStory.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BrandStoryDto {
    private List<String> resources;
    private String slogan;
    private List<String> suggestions;
    private List<String> niches;
}
