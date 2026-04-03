package com.hmh.controller.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor // final이 붙은 필드의 생성자를 자동으로 만들어줌(생성자 주입)
public class DashboardViewController {


    /**
     * 첫 페이지 접속 시 분기 처리
     */
    @GetMapping("/")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 세션이 없거나 로그인 정보가 없으면 로그인 페이지로 리다이렉트
        if (session == null || session.getAttribute("LOGIN_MEMBER") == null) {
            return "redirect:member/login";
        }

        // 로그인 되어 있으면 대시보드 페이지로 리다이렉트
        return "redirect:/dashboard";
    }

    /**
     * 대시보드 페이지 이동 (타임리프: src/main/resources/templates/dashboard.html)
     * (이 경로는 인터셉터가 보호하고 있으므로 무조건 로그인된 사람만 들어올 수 있음)
     */
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }
}
