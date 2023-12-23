package com.soim.brandme.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String token;

    @Column
    private String image;

    private String password;
    private String username;



    @Builder
    public User(String name, String email, Role role, String password, String username){
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User update(String name, String picture) {
        this.name = name;
        return this;
    }

}
