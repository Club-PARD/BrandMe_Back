package com.soim.brandme.brandStory.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDtoForEntity {
    private Long brandStoryId;
    private List<String> resources;
    private String slogan;
    private List<String> suggestions;
    private List<String> niches;
}
