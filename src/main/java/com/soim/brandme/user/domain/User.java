package com.soim.brandme.user.domain;

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

    @Column(nullable = false)
    private String role;
    private String provider;
    private String providerId;
    private String image;

    private String password;
    private String username;
    private String locale;


    @Builder
    public User(String name, String email, String role, String password,
        String username,String provider, String providerId,String image,String locale) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.image = image;
        this.locale = locale;
    }

    public User update(String name, String picture,String email) {
        this.name = name;
        this.email= email;
        this.image = picture;
        return this;
    }


}
