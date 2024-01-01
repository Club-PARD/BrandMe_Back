package com.soim.brandme.brandStory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandStoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    @ElementCollection
    @CollectionTable(name = "brand_keywords", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "brand_keyword")
    private List<String> brandKeywords;
    @ElementCollection
    @CollectionTable(name = "story_titles", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "story_title")
    private List<String> storyTitles;
    @ElementCollection
    @CollectionTable(name = "story_texts", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "story_text")
    private List<String> storyTexts;
    private String resources;
    private String target;
    private String suggestions;
}
