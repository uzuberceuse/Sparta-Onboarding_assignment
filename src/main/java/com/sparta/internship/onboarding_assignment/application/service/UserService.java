package com.sparta.internship.onboarding_assignment.application.service;

import com.sparta.internship.onboarding_assignment.application.exceptions.GlobalException;
import com.sparta.internship.onboarding_assignment.application.exceptions.NotFoundException;
import com.sparta.internship.onboarding_assignment.config.auth.JwtUtil;
import com.sparta.internship.onboarding_assignment.domain.entity.User;
import com.sparta.internship.onboarding_assignment.domain.repository.UserRepository;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignInRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpRequestDto;
import com.sparta.internship.onboarding_assignment.presentation.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


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
        userRepository.save(user);
        return SignUpResponseDto.of(user);
    }

    public String signIn(SignInRequestDto request) {

        // 유저 확인
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));

        if(user!=null){
            String token = jwtUtil.createToken(user.getUsername(), user.getRole());
            return token;
        } else {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "유저가 존재하지 않습니다.");
        }
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * username 유니크 제약 조건 검증
     * @param username
     * @return
     */
    public boolean existsByUsername(String username) {
        boolean result = userRepository.existsByUsername(username);
        return result;
    }

    /**
     * nickname 유니크 제약 조건 검증
     * @param nickname
     * @return
     */
    public boolean existsByNickname(String nickname) {
        boolean result = userRepository.existsByNickname(nickname);
        return result;
    }
}
