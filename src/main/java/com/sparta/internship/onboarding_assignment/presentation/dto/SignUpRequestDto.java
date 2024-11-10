package com.sparta.internship.onboarding_assignment.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Size(min = 8, max = 20)
    @Schema(
            title ="유저 이름",
            description = "유저의 이름 입니다."
    )
    private String username;

    @NotBlank
    @Schema(
            title = "유저 PW",
            description = "유저의 PW 입니다."
    )
    private String password;

    @NotBlank
    @Schema(
            title = "유저 닉네임",
            description = "유저의 닉네임 입니다."
    )
    private String nickname;
}
