package com.soim.brandme.brandCard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BrandCardDto {
    private String brandJob;
    private List<String> jobDetails;
}
