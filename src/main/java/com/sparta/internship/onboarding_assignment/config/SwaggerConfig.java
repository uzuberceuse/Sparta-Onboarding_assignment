package com.sparta.internship.onboarding_assignment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        final String jwtSecurityName = "bearerAuth"; // 헤더 인증 Key
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes(jwtSecurityName, securityScheme(jwtSecurityName)) // Header 토큰 인증 정보를 Swagger 문서에 등록
                )
                .info(swaggerInfo()); // 함수에 아래 문서 기본 설정을 넣어줌.
    }

    // Swagger 문서 에서 API를 호출할 때 JWT 인증 추가
    // Header 토큰 인증 정보 생성
    // 단순히 아래 정보를 추가한다 하여 JWT 토큰을 쓸 수 있는 것은 아님. 각 API마다 이 토큰을 쓰겠다고 선언을 해야함.
    private SecurityScheme securityScheme(String jwtSecurityName) {
        return new SecurityScheme()
                .name(jwtSecurityName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer") // 스키마는 Bearer 토큰을 사용하고 Key
                .bearerFormat("JWT"); // JWT로부터 인증을 한다. 토큰 Value Format
    }

    // 문서 기본 정보
    private Info swaggerInfo() {
        return new Info()
                .title("Onboarding 백엔드 과제") // 문서 이름
                .description("한달 인턴 OnBoarding backend 과제입니다.") // 문서 설명
                .version("1.0.0"); // 문서 버전
    }
}
