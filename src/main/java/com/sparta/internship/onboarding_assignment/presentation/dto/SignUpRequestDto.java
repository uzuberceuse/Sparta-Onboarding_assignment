package com.sparta.internship.onboarding_assignment.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "사용자 아이디를 입력해주세요.")
    @Size(min = 8, max = 20, message = "아이디는 최소 8자 이상, 20자 이하이어야 합니다.")
    @Schema(
            title ="유저 이름",
            description = "유저의 이름 입니다."
    )
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 15자 이하이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
            message = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    @Schema(
            title = "유저 PW",
            description = "유저의 PW 입니다."
    )
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 8, max = 20, message = "닉네임은 최소 8자 이상, 20자 이하이어야 합니다.")
    @Schema(
            title = "유저 닉네임",
            description = "유저의 닉네임 입니다."
    )
    private String nickname;
}
