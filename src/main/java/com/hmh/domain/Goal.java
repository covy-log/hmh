package com.hmh.domain;

import com.hmh.domain.constant.GoalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {
    private Long seqNo; // 목표 고유 seqNo
    private Long memberSeqNo; // 목표를 가진 회원의 seqNo (FK 역할)
    private String title; // 목표 명칭 (예: 공부, 운동)
    private int targetValue; // 목표 수치 (예: 20시간, 4회)
    private int currentValue; // 현재 달성 수치
    private String unit; // 단위 (예: 시간, 회)

    @Builder.Default
    private GoalStatus status = GoalStatus.IN_PROGRESS; // 기본값은 '진행 중'
}