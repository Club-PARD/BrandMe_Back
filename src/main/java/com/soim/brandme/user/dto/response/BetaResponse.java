package com.soim.brandme.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BetaResponse {
    String email;
    Boolean betaTested;
}
