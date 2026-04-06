package com.hmh.controller.api;

import com.hmh.domain.Member;
import com.hmh.domain.Routine;
import com.hmh.dto.Routine.RoutineSettingDto;
import com.hmh.service.RoutineService;
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
    public ResponseEntity<String> save(@Valid @RequestBody RoutineSettingDto routineSettingDto) {

        // 1. DTO를 도메인 엔티티로 변환
        Routine rt = Routine.builder()
                .title(routineSettingDto.getTitle())
                .targetValue(routineSettingDto.getTargetValue())
                .intervalWeeks(routineSettingDto.getIntervalWeeks())
                .daysOfWeek(routineSettingDto.getDaysOfWeek())
                .build();

        try {
            // todo 1.routineType 지정 2.루틴 시작일자 지정



            // 성공 시 201 Created 상태 코드 반환
            return ResponseEntity.status(HttpStatus.CREATED).body("success");

        } catch (IllegalStateException e) {
            // 중복 목표인 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
