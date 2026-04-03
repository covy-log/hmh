package com.hmh.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        // 세션이 없으면 새로 생성하지 않고 null을 반환
        HttpSession session = request.getSession(false);

        // 세션이 아예 없거나, 로그인 정보(LOGIN_MEMBER)가 없으면 튕겨냄
        if (session == null || session.getAttribute("LOGIN_MEMBER") == null) {
            // 미인증 사용자는 로그인 페이지로 리다이렉트
            // 센스 있게 원래 가려던 목적지(requestURI)를 파라미터로 달아주면 나중에 로그인 성공 시 원래 페이지로 보내주기 좋아
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false; // 더 이상 컨트롤러로 진행하지 않음
        }

        return true; // 인증된 사용자면 정상적으로 다음 컨트롤러 호출
    }
}