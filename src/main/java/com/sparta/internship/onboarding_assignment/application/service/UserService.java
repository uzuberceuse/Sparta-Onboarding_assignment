package com.sparta.internship.onboarding_assignment.application.service;

import com.sparta.internship.onboarding_assignment.application.exceptions.GlobalException;
import com.sparta.internship.onboarding_assignment.application.exceptions.NotFoundException;
import com.sparta.internship.onboarding_assignment.config.auth.jwt.JwtUtil;
import com.sparta.internship.onboarding_assignment.domain.entity.User;
import com.sparta.internship.onboarding_assignment.domain.entity.UserRoleEnum;
import com.sparta.internship.onboarding_assignment.domain.repository.UserRepository;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignInRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignInResponseDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Value("${jwt.token.refresh-expiration}")
    private Long refreshExpiration;


    // 회원가입
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto request) {

        // username 유니크 제약 조건 검증
         if(existsByUsername(request.getUsername())){
             throw new GlobalException(HttpStatus.BAD_REQUEST, "이미 존재하는 username입니다.");
         }

        // nickname 유니크 제약 조건 검증
        if(existsByNickname(request.getNickname())){
            throw new GlobalException(HttpStatus.BAD_REQUEST, "이미 존재하는 nickname입니다.");
        }

        // 사용자 등록
        User user = User.create(
                request.getUsername(),
                getEncodedPassword(request.getPassword()),
                request.getNickname());

        // 사용자 권한 임시 부여
        user.addAuthority(UserRoleEnum.USER);
        userRepository.save(user);
        return SignUpResponseDto.of(user);
    }

    public SignInResponseDto signIn(SignInRequestDto request, HttpServletResponse response) {

        // 유저 확인
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("해당 아이디를 가진 유저가 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotFoundException("비밀번호가 일치하지 않습니다.");
        }

        // 유저의 권한 정보 추출
        String role = user.getAuthorities().stream()
                .map(authority -> authority.getRole().getAuthority()) // 권한 문자열 추출
                .findFirst() // 첫 번째 권한 가져오기 (단일 권한 가정)
                .orElseThrow(() -> new IllegalStateException("유저의 권한이 설정되지 않았습니다."));

        // 토큰 반환
        return SignInResponseDto.of(issueToken(response, user.getUsername(), role));
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 토큰 발급
     */
    public String issueToken( HttpServletResponse response, String username, String role) {

        String accessToken = jwtUtil.createAccessToken(username,role);
        String refreshToken = jwtUtil.createRefreshToken(username,role);

        addAccessTokenToHeader(response, accessToken);
        addRefreshTokenToCookie(response, refreshToken);

        accessToken = jwtUtil.substringToken(accessToken);

        return accessToken;
    }

    /**
     * Access Token을 헤더에 추가
     */
    public void addAccessTokenToHeader(HttpServletResponse response, String accessToken) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
    }

    /**
     * Refresh Token을 HttpOnly 쿠키에 추가
     */
    public void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        try {
            // "Bearer " 접두사 제거 및 URL 인코딩
            String encodedRefreshToken = URLEncoder.encode(jwtUtil.substringToken(refreshToken), StandardCharsets.UTF_8.toString());

            Cookie refreshCookie = new Cookie("refreshToken", encodedRefreshToken);
            refreshCookie.setHttpOnly(true); // HttpOnly 설정
            refreshCookie.setSecure(true); // HTTPS 연결에서만 전송
            refreshCookie.setPath("/"); // 전체 애플리케이션 경로에서 사용
            refreshCookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(refreshExpiration)); // 만료 시간 설정
            response.addCookie(refreshCookie);

            log.debug("RefreshToken 쿠키 저장 완료: {}", refreshCookie.getValue());
        } catch (Exception e) {

            log.error("Failed to create and store refresh token", e);
            throw new RuntimeException("Error creating refresh token", e);
        }
    }

    /**
     * username 유니크 제약 조건 검증
     * @param username
     * @return
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * nickname 유니크 제약 조건 검증
     * @param nickname
     * @return
     */
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
