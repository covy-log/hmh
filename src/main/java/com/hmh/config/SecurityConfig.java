package com.hmh.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    // 개발 편의를 위해 시큐리티 기본 잠금을 해제하는 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 로컬 테스트를 위해 CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 일단 모든 요청(API)을 권한 없이 허용
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 스프링 시큐리티가 자체 로그인 페이지를 생성하지 않고, 우리 login.html을 쓰도록 고정
                        .successHandler(oAuth2LoginSuccessHandler)
                );
        return http.build();
    }
}