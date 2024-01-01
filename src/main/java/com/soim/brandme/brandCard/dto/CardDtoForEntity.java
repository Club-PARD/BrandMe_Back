package com.soim.brandme.brandCard.dto;

import com.soim.brandme.brandStory.dto.BrandStoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDtoForEntity {
    private Long brandCardId;
    private String brandJob;
    private String jobDetail;
}
