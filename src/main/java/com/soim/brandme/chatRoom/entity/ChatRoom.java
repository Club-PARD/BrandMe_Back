package com.soim.brandme.chatRoom.entity;

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
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String wai;
    @ElementCollection
    @CollectionTable(name = "keywords", joinColumns = @JoinColumn(name = "chat_room_id"))
    @Column(name = "keyword")
    private List<String> keywords;
    @Convert(converter = KeywordsConverter.class)
    private List<String> answers;
//    @ElementCollection
//    @CollectionTable(name = "group_keywords", joinColumns = @JoinColumn(name = "chat_room_id"))
//    @MapKeyColumn(name = "keyword_key")
//    @Column(name = "keyword_value")
//    Map<Long, List<String>> groupKeywords;
//    @Convert
//    private String groupKeywords;
    private String onePager;
    private String card;

    @Builder
    public ChatRoom(Long id,User user, String wai, List<String> keywords, List<String> answers,String onePager) {
       this.chatRoomId = id;
        this.user = user;
        this.wai = wai;
        this.keywords = keywords;
        this.answers = answers;
//        this.groupKeywords = groupKeywords;
        this.onePager = onePager;
    }

}


