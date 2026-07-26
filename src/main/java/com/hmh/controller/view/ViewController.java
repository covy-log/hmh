package com.hmh.controller.view;

import com.hmh.common.Const;
import com.hmh.domain.DailyLog;
import com.hmh.domain.Routine;
import com.hmh.domain.constant.LogStatus;
import com.hmh.domain.constant.MemberRole;
import com.hmh.domain.constant.RoutineType;
import com.hmh.dto.Routine.DailyLogDto;
import com.hmh.dto.Routine.DailyLogHistoryDto;
import com.hmh.dto.Routine.RoutineCycleStatusDto;
import com.hmh.dto.Routine.RoutineSettingDto;
import com.hmh.service.DailyLogService;
import com.hmh.service.MemberService;
import com.hmh.service.RoutineCycleService;
import com.hmh.service.RoutineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.hmh.common.util.ViewUtil.convertValueView;
import static com.hmh.common.util.ViewUtil.convertViewToDays;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final RoutineService routineService;

    private final RoutineCycleService routineCycleService;

    private final DailyLogService dailyLogService;

    private final MemberService memberService;

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
     * 상단 네비게이션에 표시할 로그인 아이디, 관리자 여부를 모델에 담기
     */
    private void addLoginIdToModel(Long memberSeqNo, Model model) {
        memberService.findBySeqNo(memberSeqNo)
                .ifPresent(member -> {
                    model.addAttribute("name", member.getName());
                    model.addAttribute("isAdmin", member.getRole() == MemberRole.ADMIN);
                });
    }

    /**
     * 일일 루틴 페이지 이동
     */
    @GetMapping("/dailyLog")
    public String dailyLogPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Long memberSeqNo = (Long) session.getAttribute(Const.LOGIN_MEMBER);
        addLoginIdToModel(memberSeqNo, model);

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

            // achieved_value 컬럼이 NUMERIC(10,2)라 정수 타입도 "20.00"처럼 내려온다. 타입별로 표시 형식을 맞춘다.
            if (dailyLogDto.getAchievedValue() != null) {
                if (dailyLogDto.getRoutineType() == RoutineType.KM) {
                    // km 타입은 DB에 미터(정수)로 저장돼 있어서, 입력창엔 km(소수 2자리)로 보여준다
                    dailyLogDto.setAchievedValue(
                            dailyLogDto.getAchievedValue().divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP)
                    );
                } else {
                    // COUNT, TIME(분): 정수 표시
                    dailyLogDto.setAchievedValue(dailyLogDto.getAchievedValue().setScale(0, RoundingMode.HALF_UP));
                }
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
        addLoginIdToModel(memberSeqNo, model);

        List<RoutineCycleStatusDto> routineCycleStatusDtoList = routineCycleService.findCurrentStatusByMemberSeqNo(memberSeqNo);

        for (RoutineCycleStatusDto status : routineCycleStatusDtoList) {
            int targetValueCnt = status.getTargetValue().intValue();
            int currentValueCnt = status.getCurrentAchievedValue().intValue();

            status.setTargetValueView(convertValueView(status.getRoutineType(), targetValueCnt));
            status.setCurrentValueView(convertValueView(status.getRoutineType(), currentValueCnt));
            status.setProgressPercent(targetValueCnt > 0
                    ? Math.min(100, (int) Math.round(currentValueCnt * 100.0 / targetValueCnt))
                    : 0);
        }

        List<DailyLogHistoryDto> dailyLogHistoryDtoList = dailyLogService.findHistoryByMemberSeqNo(memberSeqNo);

        for (DailyLogHistoryDto history : dailyLogHistoryDtoList) {
            history.setResultValueView(convertValueView(history.getRoutineType(), history.getAchievedValue().intValue()));
            history.setTargetValueView(convertValueView(history.getRoutineType(), history.getTargetValue().intValue()));
            history.setSuccess(LogStatus.DONE.equals(history.getStatus()));
        }

        model.addAttribute("routineCycleStatusDtoList", routineCycleStatusDtoList);
        model.addAttribute("dailyLogHistoryDtoList", dailyLogHistoryDtoList);
        return "routineCycleStatus";
    }

    /**
     * 루틴 설정 페이지 이동
     */
    @GetMapping("/routineSetting")
    public String routineSettingPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Long memberSeqNo = (Long) session.getAttribute(Const.LOGIN_MEMBER);
        addLoginIdToModel(memberSeqNo, model);
        List<Routine> routineList = routineService.findAllByMemberSeqNo(memberSeqNo);

        List<RoutineSettingDto> routineSettingDtoList = new ArrayList<>();

        for (Routine routine : routineList) {
            String daysOfWeekView = convertViewToDays(routine.getDaysOfWeek());

            int targetValueCnt = routine.getTargetValue().intValue();
            int dailyLimitCnt = routine.getDailyLimit().intValue();

            String targetValueView = convertValueView(routine.getRoutineType(), targetValueCnt);
            String dailyLimitView = dailyLimitCnt > 0 ? convertValueView(routine.getRoutineType(), dailyLimitCnt) : Const.UNIT_NULL;

            RoutineSettingDto routineSettingDto = RoutineSettingDto.builder()
                    .seqNo(routine.getSeqNo())
                    .title(routine.getTitle())
                    .routineType(routine.getRoutineType())
                    .targetValueView(targetValueView)
                    .dailyLimitView(dailyLimitView)
                    .daysOfWeekView(daysOfWeekView)
                    .startYmd(routine.getStartYmd())
                    .build();

            routineSettingDtoList.add(routineSettingDto);
        }

        model.addAttribute("routineSettingDtoList", routineSettingDtoList);
        return "routineSetting";
    }
}
