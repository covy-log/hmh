package com.hmh.domain;

import com.hmh.domain.constant.RoutineStatus;
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
public class RoutineCycle {
    /**
     * 고유번호 (PK)
     */
    private Long seqNo;

    /**
     * 부모 루틴 고유번호 (FK)
     */
    private Long routineSeqNo;

    /**
     * 회원 고유번호 (조회 성능 최적화를 위해 추가 추천)
     */
    private Long memberSeqNo;

    /**
     * 몇 회차 주기인지 (예: 1회차, 2회차...)
     * 사용자에게 "나의 5번째 대청소 루틴" 등을 보여주기 좋음
     */
    private int cycleNumber;

    /**
     * 이번 주기의 시작일 (예: 2026-04-06 월요일)
     */
    private LocalDate startYmd;

    /**
     * 이번 주기의 종료일 (예: 2026-04-12 일요일)
     */
    private LocalDate endYmd;

    /**
     * [중요] 주기가 생성될 당시의 목표 수치 스냅샷
     * 사용자가 중간에 목표를 20시간 -> 10시간으로 줄여도, 과거 주기의 목표치 기록은 변하면 안 됨
     */
    private int targetValue;

    /**
     * [선택] 이번 주기 동안 현재까지 달성한 누적 수치
     * 매번 daily_log를 다 더해서 계산해도 되지만, 여기에 누적값을 업데이트해두면 통계 화면 띄울 때 엄청 빠름
     */
    private int currentAchievedValue;

    /**
     * 주기 상태
     */
    private RoutineStatus status;

    /**
     * 생성일시
     */
    private LocalDateTime createAt;

    /**
     * 변경일시
     */
    private LocalDateTime changeAt;
}
