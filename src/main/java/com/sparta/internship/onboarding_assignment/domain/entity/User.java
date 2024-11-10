package com.sparta.internship.onboarding_assignment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // Enum의 이름 그대로 데이터베이스에 저장
    private UserRole role;

    public static User create(String username, String encodedPassword, String nickname) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .role(UserRole.USER)
                .build();
    }
}