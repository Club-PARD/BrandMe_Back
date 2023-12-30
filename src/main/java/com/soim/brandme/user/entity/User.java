package com.soim.brandme.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private String role="ROLE_USER";
    private String provider;
    private String providerId;
    private String image;
    private boolean firstLogin;
    private String password;
    private String username;
    private String locale;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY,mappedBy = "user")
    private List<ChatRoom> chatRooms = new ArrayList<>();


    @Builder
    public User(String name, String email, String role, String password, Boolean firstLogin,
        String username,String provider, String providerId,String image,String locale,List<ChatRoom> chatRooms) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstLogin = (firstLogin != null) ? firstLogin : true;
        this.role = (role != null) ? role : "ROLE_USER";
        this.provider = provider;
        this.providerId = providerId;
        this.image = image;
        this.locale = locale;
        this.chatRooms = chatRooms;
    }

    public User update(String name) {
        this.name = name;
        return this;
    }


}
