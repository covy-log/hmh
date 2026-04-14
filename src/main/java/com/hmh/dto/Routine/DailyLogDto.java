package com.hmh.dto.Routine;

import com.hmh.domain.constant.LogStatus;
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
public class DailyLogDto {

    /**
     * 고유 번호
     */
    private long seqNo;

    /**
     * 루틴명
     */
    private String title;

    /**
     * 달성 상태
     */
    private LogStatus status;

    /**
     * 날짜
     */
    private String todoYmd;

    /**
     * 달성 수치 (예: 20시간, 4회)
     */
    private BigDecimal achievedValue;

    /**
     * 목표 수치 (예: 20시간, 4회)
     */
    private BigDecimal targetValue;

    /**
     * 루틴 타입 (예: CHECK, COUNT, TIME)
     */
    private RoutineType routineType;

    /**
     * 루틴 타입에 따른 단위명
     */
    private String unit;

    public void setRoutineType(RoutineType routineType) {
        setUnit(routineType.getDescription());
        this.routineType = routineType;
    }
}
