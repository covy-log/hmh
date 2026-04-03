package com.hmh.controller.view;

import com.hmh.service.RoutineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeViewController {

    private final RoutineService routineService;

    /**
     * 첫 페이지 접속 시 분기 처리
     */
    @GetMapping("/")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 세션이 없거나 로그인 정보가 없으면 로그인 페이지로 리다이렉트

        if (session == null || session.getAttribute("LOGIN_MEMBER") == null) {
            return "member/login";
        }

        return "dailyLog";
    }

    /**
     * 일일 루틴 페이지 이동
     */
    @GetMapping("/dailyLog")
    public String dailyLogPage() {


        return "dailyLog";
    }

    /**
     * 루틴 현황 페이지 이동
     */
    @GetMapping("/routineCycleStatus")
    public String routineCycleStatusPage() {


        return "routineCycleStatus";
    }

    /**
     * 목표 설정 페이지 이동
     */
    @GetMapping("/routineSetting")
    public String routineSettingPage() {


        return "routineSetting";
    }
}
