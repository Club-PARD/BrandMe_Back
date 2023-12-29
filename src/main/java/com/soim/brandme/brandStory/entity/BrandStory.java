package com.soim.brandme.brandStory.entity;

import com.soim.brandme.chatRoom.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class BrandStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandStoryId;
    @ManyToOne(fetch = FetchType.LAZY)
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
}
