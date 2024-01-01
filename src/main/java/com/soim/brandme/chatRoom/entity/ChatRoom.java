package com.soim.brandme.chatRoom.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soim.brandme.brandCard.entity.BrandCard;
import com.soim.brandme.brandStory.entity.BrandStory;
import com.soim.brandme.chatRoom.application.GroupKeywordsMapConverter;
import com.soim.brandme.chatRoom.application.KeywordsConverter;
import com.soim.brandme.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String chatNickName;
    @ElementCollection
    @CollectionTable(name = "keywords", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Column(name = "keyword")
    private List<String> keywords;
    @Convert(converter = KeywordsConverter.class)
    private List<String> answers;
    @OneToOne(mappedBy="chatRoom", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private BrandStory brandStory;
    @OneToOne(mappedBy="chatRoom", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private BrandCard brandCard;
//    @ElementCollection
//    @CollectionTable(name = "group_keywords", joinColumns = @JoinColumn(name = "chat_room_id"))
//    @MapKeyColumn(name = "keyword_key")
//    @Column(name = "keyword_value")
//    Map<Long, List<String>> groupKeywords;
//    @Convert(converter = GroupKeywordsMapConverter.class)
//    private String groupKeywords;
    @Builder
    public ChatRoom(Long id,User user, String chatNickName, List<String> keywords, List<String> answers, BrandCard brandCard, BrandStory brandStory) {
       this.chatRoomId = id;
        this.user = user;
        this.chatNickName = chatNickName;
        this.keywords = keywords;
        this.answers = answers;
//        this.groupKeywords = groupKeywords;
        this.brandCard = brandCard;
        this.brandStory = brandStory;
    }

}


