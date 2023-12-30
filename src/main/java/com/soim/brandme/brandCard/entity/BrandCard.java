package com.soim.brandme.brandCard.entity;

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
@AllArgsConstructor
@NoArgsConstructor
public class BrandCard {
    @Id
    private Long brandCardId;
    @JsonBackReference
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    private String brandJob;
    @ElementCollection
    @CollectionTable(name = "jobDetails", joinColumns = @JoinColumn(name = "brand_card_id"))
    @Column(name = "detail")
    private List<String> jobDetails;

    @Builder
    public BrandCard(String brandJob, List<String> jobDetails) {
        this.brandJob = brandJob;
        this.jobDetails = jobDetails;
    }
}
