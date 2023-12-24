package com.soim.brandme.user.presentation.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    String name;
    String email;
    String image;
//    String role;

}
