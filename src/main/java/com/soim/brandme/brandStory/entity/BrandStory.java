package com.soim.brandme.brandStory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BrandStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandStoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    private String identity;
    private String identity_explanation;
    private String competency;
    private String target;
    private String contentsRecommendation;
    private String strategy;
    @ElementCollection
    @CollectionTable(name = "brand_keywords", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "brand_keyword")
    private List<String> brandKeywords;
    @ElementCollection
    @CollectionTable(name = "story_headlines", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "story_headline")
    private List<String> storyHeadlines;
    @ElementCollection
    @CollectionTable(name = "story_contents", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "story_content")
    private List<String> storyContents;


}
