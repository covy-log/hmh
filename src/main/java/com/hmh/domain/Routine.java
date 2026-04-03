package com.hmh.domain;

import com.hmh.domain.constant.RoutineStatus;
import com.hmh.domain.constant.RoutineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Routine {

    /**
     * 고유번호
     */
    private Long seqNo;

    /**
     * 회원 고유번호
     */
    private Long memberSeqNo;

    /**
     * 루틴명
     */
    private String title;

    /**
     * 루틴 목표 수치 (예: 20시간, 4회)
     */
    private int targetValue;

    /**
     * 루틴 목표 타입 (예: CHECK, COUNT, TIME)
     */
    private RoutineType routineType;

    /**
     * 반복 주기 (단위: 주) (예: 1=매주, 2=격주)
     */
    private int intervalWeeks;

    /**
     * 수행 요일 (예:"1,3,5")
     * 1:월요일, 2:화요일 ... 7:일요일
     */
    private String daysOfWeek;

    /**
     * 루틴 시작(기준) 일자 (주기 계산을 위한 기준점)
     */
    private LocalDate startYmd;

    /**
     * 생성일시
     */
    private LocalDateTime createAt;

    /**
     * 변경일시
     */
    private LocalDateTime changeAt;

    @Builder.Default
    private RoutineStatus status = RoutineStatus.IN_PROGRESS; // 기본값은 '진행 중'
}