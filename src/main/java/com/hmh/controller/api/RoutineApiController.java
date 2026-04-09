package com.hmh.controller.api;

import com.hmh.domain.Routine;
import com.hmh.dto.Routine.RoutineSettingDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routine")
public class RoutineApiController {

    private final RoutineService routineService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody RoutineSettingDto routineSettingDto,
                                       HttpServletRequest request) {
        HttpSession session = request.getSession();
        long memberSeqNo = (long) session.getAttribute("LOGIN_MEMBER");

        // 1. DTO를 도메인 엔티티로 변환
        Routine routine = Routine.builder()
                .title(routineSettingDto.getTitle())
                .routineType(routineSettingDto.getRoutineType())
                .targetValue(routineSettingDto.getTargetValue())
                .intervalWeeks(routineSettingDto.getIntervalWeeks())
                .daysOfWeek(routineSettingDto.getDaysOfWeek()) // 1,3,5
                .startYmd(routineSettingDto.getStartYmd())
                .memberSeqNo(memberSeqNo)
                .build();

        try {

            routineService.save(routine);

            // 성공 시 201 Created 상태 코드 반환
            return ResponseEntity.status(HttpStatus.CREATED).body("success");

        } catch (IllegalStateException e) {
            // 중복 루틴인 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
