package com.soim.brandme.auth.dto.resonse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long userId;
    private Boolean firstLogin;
    String nickname;
}
