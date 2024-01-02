package com.soim.brandme.brandCard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BrandCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandCardId;
    private String identity;
    private String identity_explanation;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
}
