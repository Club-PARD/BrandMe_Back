package com.soim.brandme.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NicknameResponse {
    private String name;
    private String email;
    private String image;
    private String username;
    private boolean firstLogin;
}
