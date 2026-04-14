package com.hmh.controller.view;

import com.hmh.common.Const;
import com.hmh.domain.DailyLog;
import com.hmh.domain.Routine;
import com.hmh.dto.Routine.DailyLogDto;
import com.hmh.service.DailyLogService;
import com.hmh.service.RoutineCycleService;
import com.hmh.service.RoutineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final RoutineService routineService;

    private final RoutineCycleService routineCycleService;

    private final DailyLogService dailyLogService;

    /**
     * 첫 페이지 접속 시 분기 처리
     */
    @GetMapping("/")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 세션이 없거나 로그인 정보가 없으면 로그인 페이지로 리다이렉트

        if (session == null || session.getAttribute(Const.LOGIN_MEMBER) == null) {
            return "member/login";
        }

        return "dailyLog";
    }

    /**
     * 일일 루틴 페이지 이동
     */
    @GetMapping("/dailyLog")
    public String dailyLogPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Long memberSeqNo = (Long) session.getAttribute(Const.LOGIN_MEMBER);

        DailyLog dailyLog = DailyLog.builder()
                .memberSeqNo(memberSeqNo)
                .todoYmd(LocalDate.now())
                .build();

        List<DailyLogDto> dailyLogDtoList = dailyLogService.findAllOfTodayByDailyLog(dailyLog);

        int totalCount = dailyLogDtoList.size();
        int completedCount = 0;

        for (DailyLogDto dailyLogDto : dailyLogDtoList) {
            if ("DONE".equals(dailyLogDto.getStatus().name())) { // NPE 방지를 위해 "DONE"을 앞으로 배치
                completedCount++;
            }
        }

        double achievementRate = 0.0;

        if (totalCount > 0) {

            double rawRate = ((double) completedCount / totalCount) * 100;

            achievementRate = Math.round(rawRate * 10) / 10.0;
        }

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("achievementRate", achievementRate);
        model.addAttribute("dailyLogDtoList", dailyLogDtoList);
        return "dailyLog";
    }

    /**
     * 루틴 현황 페이지 이동
     */
    @GetMapping("/routineCycleStatus")
    public String routineCycleStatusPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long memberSeqNo = (Long) session.getAttribute(Const.LOGIN_MEMBER);
        List<Routine> routineList = routineService.findAllByMemberSeqNo(memberSeqNo);

        model.addAttribute("routineList", routineList);
        return "routineCycleStatus";
    }

    /**
     * 루틴 설정 페이지 이동
     */
    @GetMapping("/routineSetting")
    public String routineSettingPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Long memberSeqNo = (Long) session.getAttribute(Const.LOGIN_MEMBER);
        List<Routine> routineList = routineService.findAllByMemberSeqNo(memberSeqNo);

        model.addAttribute("routineList", routineList);
        return "routineSetting";
    }
}
