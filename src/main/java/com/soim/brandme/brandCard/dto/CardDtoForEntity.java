package com.soim.brandme.brandCard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDtoForEntity {
    private Long brandCardId;
    private String brandJob;
    private String jobDetail;
}
