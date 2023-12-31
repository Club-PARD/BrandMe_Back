package com.soim.brandme.user.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    String name;
    String email;
    String picture;
    String username;
//    String role;
}
