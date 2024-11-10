package com.sparta.internship.onboarding_assignment.presentation.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SignInResponseDto {

    private String token;

    public static SignInResponseDto of(final String token) {
        return SignInResponseDto.builder()
                .token(token)
                .build();
    }
}
