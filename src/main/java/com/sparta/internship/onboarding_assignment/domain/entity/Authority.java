package com.sparta.internship.onboarding_assignment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // Enum의 이름 그대로 데이터베이스에 저장
    private UserRoleEnum role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public static Authority create(User user, UserRoleEnum role) {
        return Authority.builder()
                .user(user)
                .role(role)
                .build();
    }
}
