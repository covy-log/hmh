package com.hmh.controller.api;

import com.hmh.domain.DailyLog;
import com.hmh.domain.Routine;
import com.hmh.domain.RoutineCycle;
import com.hmh.dto.Routine.RoutineSettingDto;
import com.hmh.service.DailyLogService;
import com.hmh.service.RoutineCycleService;
import com.hmh.service.RoutineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routine")
public class RoutineApiController {

    private final RoutineService routineService;

    private final RoutineCycleService routineCycleService;

    private final DailyLogService dailyLogService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody RoutineSettingDto routineSettingDto,
                                       HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            long memberSeqNo = (long) session.getAttribute("LOGIN_MEMBER");

            Routine routine = Routine.builder()
                    .title(routineSettingDto.getTitle())
                    .routineType(routineSettingDto.getRoutineType())
                    .targetValue(routineSettingDto.getTargetValue())
                    .intervalWeeks(routineSettingDto.getIntervalWeeks())
                    .daysOfWeek(routineSettingDto.getDaysOfWeek()) // 1,3,5
                    .startYmd(routineSettingDto.getStartYmd())
                    .memberSeqNo(memberSeqNo)
                    .build();


            // 루틴 저장
            routineService.save(routine);

            LocalDate today = LocalDate.now();
            LocalDate startYmd = routine.getStartYmd();

            // 당장 이번주 부터라면 한주 사이클 생성
            if (!startYmd.isAfter(today)) {

                LocalDate endYmd = startYmd.plusDays(6);

                RoutineCycle routineCycle = RoutineCycle.builder()
                        .routineSeqNo(routine.getSeqNo())
                        .memberSeqNo(memberSeqNo)
                        .cycleNumber(1)
                        .startYmd(startYmd)
                        .endYmd(endYmd)
                        .targetValue(routine.getTargetValue())
                        .build();

                routineCycleService.save(routineCycle);

                String todayOfWeek = String.valueOf(today.getDayOfWeek().getValue());
                boolean isActiveDay = routine.getDaysOfWeek().contains(todayOfWeek);

                // 수행 요일에 해당할 경우 일일 루틴 생성
                if (isActiveDay) {
                    DailyLog dailyLog = DailyLog.builder()
                            .cycleSeqNo(routineCycle.getSeqNo())
                            .memberSeqNo(memberSeqNo)
                            .todoYmd(today)
                            .build();

                    dailyLogService.save(dailyLog);
                }
            }

            // 성공 시 201 Created 상태 코드 반환
            return ResponseEntity.status(HttpStatus.CREATED).body("success");

        } catch (IllegalStateException e) {
            // 중복 루틴인 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
