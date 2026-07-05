package com.hmh.dto.Routine;

import com.hmh.domain.constant.RoutineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineCycleStatusDto {

    /**
     * 루틴명
     */
    private String title;

    /**
     * 루틴 타입 (예: CHECK, COUNT, TIME, KM)
     */
    private RoutineType routineType;

    /**
     * 이번 주기 목표 수치
     */
    private BigDecimal targetValue;

    /**
     * 이번 주기 현재까지 달성한 누적 수치
     */
    private BigDecimal currentAchievedValue;

    /**
     * 목표 수치 view (예: 20시간, 5km)
     */
    private String targetValueView;

    /**
     * 현재 달성 수치 view
     */
    private String currentValueView;

    /**
     * 달성률 (%)
     */
    private int progressPercent;
}
