package com.hmh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController {
    /**
     * 로그인 페이지 이동 (타임리프: src/main/resources/templates/login.html)
     */
    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

    /**
     * 회원가입 페이지 이동 (타임리프: src/main/resources/templates/signup.html)
     */
    @GetMapping("/signup")
    public String signupPage() {
        return "member/signup";
    }
}
