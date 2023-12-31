package com.soim.brandme.chatRoom.entity;

import com.soim.brandme.converter.KeywordsConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmbedGroupKeyword {
    @Convert(converter = KeywordsConverter.class)
    private List<String> groupKeyword;
}
