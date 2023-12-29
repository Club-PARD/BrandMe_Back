package com.soim.brandme.brandCard.domain;

import com.soim.brandme.chatRoom.domain.ChatRoom;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class BrandCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandCardId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    private String brandJob;
    @ElementCollection
    @CollectionTable(name = "jobDetails", joinColumns = @JoinColumn(name = "brand_card_id"))
    @Column(name = "detail")
    private List<String> jobDetails;
}
