package com.hmh.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalRecord {
    private Long seqNo; // 기록 고유 seqNo
    private Long goalSeqNo; // 어떤 목표에 대한 기록인가? (Goal의 seqNo)
    private LocalDateTime recordDate; // 기록한 날짜 (예: 2026-04-02)
    private int achievedValue; // 그날 달성한 수치 (예: 2시간 공부했으면 2)
    private int status; // 상태 (0:비활성화, 1:활성화)
}
