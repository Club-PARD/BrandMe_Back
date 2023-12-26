package com.soim.brandme.chatRoom.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    private String finalSentence;
    private String wai;
    @Convert(converter = KeywordsConverter.class)
    private List<String> keywords;
//    @Convert
//    private String groupKeywords;
    private String onePager;
    private String card;
}


