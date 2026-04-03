package com.hmh.domain;

import com.hmh.domain.constant.LogStatus;
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
public class DailyLog {

    /**
     * 일일 기록 고유번호 (PK)
     */
    private Long seqNo;

    /**
     * 부모 사이클 고유번호 (FK - RoutineCycle)
     * 이 기록이 어느 주기(예: 4월 1주차 사이클)에 속하는지 통계를 내기 위한 핵심 연결고리.
     */
    private Long cycleSeqNo;

    /**
     * 회원 고유번호
     */
    private Long memberSeqNo;

    /**
     * 루틴을 수행해야 하는 지정 날짜 (예: 2026-04-03)
     * 데몬이 해당 요일에 맞춰서 미리 데이터를 깔아둘 때 기준이 되는 날짜.
     */
    private LocalDate todoYmd;

    /**
     * 일일 수행 상태
     */
    @Builder.Default
    private LogStatus status = LogStatus.TODO;

    /**
     * 오늘 하루 동안 달성한 수치
     * - 누적형(시간, 횟수): 오늘 3시간을 했으면 3
     * - 체크형(단순 완료): 완료했으면 1, 아니면 0으로 처리.
     */
    @Builder.Default
    private int achievedValue = 0;

    /**
     * 사용자가 실제로 '완료' 처리를 한 시간
     * (단순히 targetDate만 아는 것보다, 유저가 주로 아침에 하는지 밤에 하는지 데이터 분석하기 좋아)
     */
    private LocalDateTime completedAt;

    /**
     * 생성일시
     */
    private LocalDateTime createAt;

    /**
     * 변경일시
     */
    private LocalDateTime changeAt;
}