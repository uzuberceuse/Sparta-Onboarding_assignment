package com.sparta.internship.onboarding_assignment.presentation.dto;

import com.sparta.internship.onboarding_assignment.domain.entity.Authority;
import com.sparta.internship.onboarding_assignment.domain.entity.User;
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

        public static AuthorityResponse of(Authority authority) {
            return AuthorityResponse.builder()
                    .authorityName(authority.getRole().name()) // 권한 이름을 반환
                    .build();
        }
    }

    public static SignUpResponseDto of(final User user) {

        // User의 authorities 리스트를 AuthorityResponse로 변환
        List<AuthorityResponse> authorityResponses = user.getAuthorities().stream()
                .map(AuthorityResponse::of) // 각 Authority를 AuthorityResponse로 변환
                .toList();

        return SignUpResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorities(authorityResponses) // 변환된 권한 리스트 설정
                .build();
    }
}