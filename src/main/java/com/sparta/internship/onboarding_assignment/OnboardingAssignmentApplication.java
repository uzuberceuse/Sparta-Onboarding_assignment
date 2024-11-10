package com.sparta.internship.onboarding_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnboardingAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnboardingAssignmentApplication.class, args);
    }

}
