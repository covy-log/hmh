package com.hmh.dto.Routine;

import com.hmh.domain.constant.RoutineType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyLogDto {

    /**
     * 루틴명
     */
    @NotBlank(message = "루틴 명을 입력해주세요.")
    private String title;

    /**
     * 날짜
     */
    private String todoYmd;

    /**
     * 달성 수치 (예: 20시간, 4회)
     */
    private int achievedValue;

    /**
     * 루틴 타입 (예: CHECK, COUNT, TIME)
     */
    private RoutineType routineType;

}
