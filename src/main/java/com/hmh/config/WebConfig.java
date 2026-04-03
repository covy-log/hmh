package com.hmh.config;

import com.hmh.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 인터셉터 체인 순서
                .addPathPatterns("/**") // 기본적으로 모든 경로에 인터셉터 적용
                .excludePathPatterns(
                        "/", "/login", "/signup", // 첫 페이지, 로그인, 회원가입 페이지는 통과
                        "/api/**", // API 통과
                        "/css/**", "/js/**", "/*.ico", "/error" // 정적 리소스 및 에러 페이지 통과
                );
    }
}