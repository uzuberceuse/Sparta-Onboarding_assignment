package com.sparta.internship.onboarding_assignment.presentation.controller;

import com.sparta.internship.onboarding_assignment.application.service.UserService;
import com.sparta.internship.onboarding_assignment.config.auth.jwt.JwtUtil;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignInRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignInResponseDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User 서비스", description = "User API 입니다.")
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param request
     * @return
     */
    @Operation(summary = "회원 가입", description = "회원 가입 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패"),
            @ApiResponse(responseCode = "500", description = "회원가입 오류")
    })
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
    @Operation(summary = "로그인", description = "로그인 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패"),
            @ApiResponse(responseCode = "500", description = "로그인 오류")
    })
    @PostMapping("/signIn")
    public ResponseEntity<SignInResponseDto> signIn(@Valid @RequestBody SignInRequestDto request,  HttpServletResponse response) {

        final SignInResponseDto result = userService.signIn(request, response);
        return ResponseEntity.ok(result);
    }
}