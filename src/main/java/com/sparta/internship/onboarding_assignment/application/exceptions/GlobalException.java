package com.sparta.internship.onboarding_assignment.application.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    HttpStatus statusCode;
    String message;
    int code;

    public GlobalException(HttpStatus statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
        this.code = statusCode.value();
    }
}
