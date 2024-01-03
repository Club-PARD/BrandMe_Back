package com.soim.brandme.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NicknameResponse {
    private String name;
    private String email;
    private String picture;
    private String nickname;
}
