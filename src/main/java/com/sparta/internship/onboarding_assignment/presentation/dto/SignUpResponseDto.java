package com.sparta.internship.onboarding_assignment.presentation.dto;

import com.sparta.internship.onboarding_assignment.domain.entity.User;
import com.sparta.internship.onboarding_assignment.domain.entity.UserRole;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SignUpResponseDto {

    private String username;
    private String nickname;
    private List<AuthorityResponse> authorities;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(access = AccessLevel.PRIVATE)
    public static class AuthorityResponse {
        private String authorityName;

        public static AuthorityResponse of(User user) {
            return AuthorityResponse.builder()
                    .authorityName(user.getRole().getAuthority()) // 역할 이름을 문자열로 설정
                    .build();
        }
    }

    public static SignUpResponseDto of(final User user) {
        return SignUpResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorities(List.of(AuthorityResponse.of(user))) // 리스트로 반환
                .build();
    }
}