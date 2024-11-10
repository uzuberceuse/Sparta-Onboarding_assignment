package com.sparta.internship.onboarding_assignment.presentation.controller;

import com.sparta.internship.onboarding_assignment.application.service.UserService;
import com.sparta.internship.onboarding_assignment.config.auth.JwtUtil;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignInRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignInResponseDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param request
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(
            @RequestBody @Valid SignUpRequestDto request) {
        final SignUpResponseDto response = userService.signUp(request);
        return ResponseEntity.ok(response);
    }


    /**
     * 로그인
     * @param request
     * @return
     */
    @PostMapping("/signIn")
    public ResponseEntity<SignInResponseDto> signIn(@Valid @RequestBody SignInRequestDto request) {
        // 토큰 생성
        final String token = userService.signIn(request);
        final SignInResponseDto response = SignInResponseDto.of(token);

        // 헤더와 JSON 본문에 동시에 token 포함
        return ResponseEntity.ok()
                .header(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.BEARER_PREFIX + token) // 헤더에 토큰 추가
                .body(response); // JSON 응답 본문에 토큰 포함
    }
}