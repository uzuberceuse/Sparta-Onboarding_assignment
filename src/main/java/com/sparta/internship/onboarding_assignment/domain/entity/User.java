package com.sparta.internship.onboarding_assignment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorities = new ArrayList<>();

    public static User create(String username, String encodedPassword, String nickname) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .build();
    }

    public void addAuthority(UserRoleEnum role) {
        Authority authority = Authority.create(this, role);
        this.authorities.add(authority);
    }
}