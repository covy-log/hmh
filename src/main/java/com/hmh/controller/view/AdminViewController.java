package com.hmh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    /**
     * 관리자 메인 페이지 이동 (AdminCheckInterceptor 에서 role=ADMIN 여부를 먼저 검증함)
     */
    @GetMapping("/admin")
    public String adminMainPage() {
        return "admin/main";
    }
}
