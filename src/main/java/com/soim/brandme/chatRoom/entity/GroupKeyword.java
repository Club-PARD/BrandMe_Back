package com.soim.brandme.chatRoom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class GroupKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "group_keyword_values", joinColumns = @JoinColumn(name = "group_keyword_id"))
    @Column(name = "keyword_value")
    private List<String> keywordValues;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "chat_room_id") // ChatRoom 엔티티의 ID를 외래 키로 사용
    private ChatRoom chatRoom;

    @Builder
    public GroupKeyword(Long id,List<String> keywordValues, ChatRoom chatRoom) {
        this.id = id;
        this.keywordValues = keywordValues;
        this.chatRoom = chatRoom;
    }
}
