package com.sparta.internship.onboarding_assignment.config.auth.jwt;

import com.sparta.internship.onboarding_assignment.domain.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    // 토큰 만료시간
    @Value("${jwt.token.access-expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.token.refresh-expiration}")
    private long refreshTokenExpiration;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private String username = "usertest";
    private String role = UserRoleEnum.USER.name();

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secretKey, accessTokenExpiration, refreshTokenExpiration);
    }

    @Test
    @DisplayName("access 토큰 발행 및 검증 테스트")
    void testAccessToken() {

        String accessToken = jwtUtil.createAccessToken(username, role);
        // accessToken 생성 확인
        assertNotNull(accessToken, "access 토큰이 발급되지 않았습니다.");

        accessToken = jwtUtil.substringToken(accessToken);
        // accessToken 검증 확인
        assertTrue(jwtUtil.validateToken(accessToken), "해당 access 토큰은 유효하지 않습니다.");
    }

    @Test
    @DisplayName("refresh 토큰 발행 및 검증 테스트")
    void testRefreshToken() {

        String refreshToken = jwtUtil.createRefreshToken(username, role);
        // refreshToken 생성 확인
        assertNotNull(refreshToken, "refresh 토큰이 발급되지 않았습니다.");

        refreshToken = jwtUtil.substringToken(refreshToken);
        // refreshToken 유효성 검증 확인;
        assertTrue(jwtUtil.validateToken(refreshToken), "해당 refresh 토큰은 유효하지 않습니다.");
    }
}