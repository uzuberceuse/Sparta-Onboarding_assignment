package com.sparta.internship.onboarding_assignment.domain.repository;

import com.sparta.internship.onboarding_assignment.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
