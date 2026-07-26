package com.hmh.dto.Routine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 루틴 수정 요청 DTO
 * 수정 가능한 항목은 목표 수치(targetValue)와 1일 최대 한도(dailyLimit) 뿐이다.
 * (변경 사항은 데몬이 다음 주 사이클을 만들 때 반영된다)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineUpdateDto {

    /**
     * 루틴 목표 수치 (등록과 동일한 원본 단위: 예 TIME=분, KM=미터, CHECK/COUNT=횟수)
     */
    private BigDecimal targetValue;

    /**
     * 1일 최대 한도 수치 (0이면 한도 없음)
     */
    private BigDecimal dailyLimit;
}
