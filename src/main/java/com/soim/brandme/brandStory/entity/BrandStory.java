package com.soim.brandme.brandStory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class BrandStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandStoryId;
    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    @ElementCollection
    @CollectionTable(name = "resources", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "resource")
    private List<String> resources;
    private String slogan;
    @ElementCollection
    @CollectionTable(name = "suggestions", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "suggestion")
    private List<String> suggestions;
    @ElementCollection
    @CollectionTable(name = "niches", joinColumns = @JoinColumn(name = "brand_story_id"))
    @Column(name = "niche")
    private List<String> niches;

        @Builder
        public BrandStory(ChatRoom chatRoom, List<String> resources, String slogan, List<String> suggestions, List<String> niches) {
        this.chatRoom = chatRoom;
        this.resources = resources;
        this.slogan = slogan;
        this.suggestions = suggestions;
        this.niches = niches;
    }
}
