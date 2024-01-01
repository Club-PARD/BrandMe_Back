package com.soim.brandme.user.dto.response;

import com.soim.brandme.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityToDto {
    Long id;
    public String name;
    public String email;
    public String nickname;
    public UserEntityToDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getUsername();
    }
}
