package com.soim.brandme.auth.presentation.resonse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long userId;
    private Boolean firstLogin;
}
