package com.hmh.dto.Routine;

import com.hmh.domain.constant.LogStatus;
import com.hmh.domain.constant.RoutineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyLogHistoryDto {

    /**
     * 수행 일자
     */
    private LocalDate todoYmd;

    /**
     * 루틴명
     */
    private String title;

    /**
     * 루틴 타입
     */
    private RoutineType routineType;

    /**
     * 일일 수행 상태
     */
    private LogStatus status;

    /**
     * 달성 수치
     */
    private BigDecimal achievedValue;

    /**
     * 해당 주기의 목표 수치
     */
    private BigDecimal targetValue;

    /**
     * 달성 수치 view
     */
    private String resultValueView;

    /**
     * 목표 수치 view
     */
    private String targetValueView;

    /**
     * 성공 여부 (완료 처리 됐는지)
     */
    private boolean success;
}
