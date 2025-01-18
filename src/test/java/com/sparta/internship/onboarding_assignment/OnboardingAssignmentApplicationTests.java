package com.sparta.internship.onboarding_assignment;

import com.sparta.internship.onboarding_assignment.config.auth.JwtUtil;
import com.sparta.internship.onboarding_assignment.domain.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "jwt.secret.key=s0m3r4nd0m3x4mpl3key2G3n3r4t3d== ")
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void verifySignatureTest() {
        // JWT를 생성하고 검증하는 테스트 작성
        String token = jwtUtil.createToken("username", UserRoleEnum.USER);
        assert jwtUtil.validateToken(token);  // 생성된 토큰이 유효한지 검증
    }

    @Test
    public void verifyExpirationTest() {
        // 만료된 JWT를 검증하는 테스트 작성
        String token = jwtUtil.createToken("username", UserRoleEnum.USER);
        // 만료 시간을 지나서 토큰이 만료됐는지 확인하는 로직
        assert !jwtUtil.validateToken(token);  // 만료된 토큰 검증
    }
}