package com.hmh.domain;

import com.hmh.domain.constant.DaemonLogStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DaemonLog {

    /**
     * 데몬 실행 이력 고유번호 (PK)
     */
    private Long seqNo;

    /**
     * 실행된 데몬(배치) 이름 (예: RoutineCycleDaemon, DailyLogDaemon)
     */
    private String daemonName;

    /**
     * 배치 시작 일시
     */
    private LocalDateTime startDt;

    /**
     * 배치 종료 일시
     */
    private LocalDateTime endDt;

    /**
     * 처리 성공 건수
     */
    @Builder.Default
    private int successCount = 0;

    /**
     * 처리 실패 건수
     */
    @Builder.Default
    private int failCount = 0;

    /**
     * 배치 실행 상태
     */
    @Builder.Default
    private DaemonLogStatus status = DaemonLogStatus.RUNNING;

    /**
     * 실패 건별 에러 메시지 요약 ([routine_seq_no] 메시지 형태로 개행 이어붙임)
     */
    private String errMsg;
}
