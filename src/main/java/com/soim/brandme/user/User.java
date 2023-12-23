package com.soim.brandme.user;

import com.soim.brandme.user.presentation.request.UserRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

//    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String role;
    private String provider;
    private String providerId;
    @Column
    private String image;

    private String password;
    private String username;



    @Builder
    public User(String name, String email, String role, String password,
        String username,String provider, String providerId,String image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.image = image;
    }

    public User update(String name, String picture) {
        this.name = name;
        return this;
    }

    public static User from(UserRequest userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .image(userRequest.getImage())
                .role(userRequest.getRole())
                .build();
    }

}
