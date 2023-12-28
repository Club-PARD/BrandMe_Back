package com.soim.brandme.chatRoom.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class GroupKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String keywordKey;
    private String keywordValue;
}
