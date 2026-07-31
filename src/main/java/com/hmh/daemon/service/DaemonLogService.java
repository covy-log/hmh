package com.hmh.daemon.service;

import com.hmh.domain.DaemonLog;
import com.hmh.domain.constant.DaemonLogStatus;
import com.hmh.repository.DaemonLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DaemonLogService {

    private final DaemonLogMapper daemonLogMapper;

    /**
     * 배치 시작 기록. status = RUNNING으로 저장하고, 종료 시 update에 쓸 seqNo를 반환한다.
     * @param daemonName 배치 이름 (예: RoutineCycleDaemon, DailyLogDaemon)
     * @return 생성된 daemon_log의 seqNo
     */
    public Long start(String daemonName) {
        DaemonLog daemonLog = DaemonLog.builder()
                .daemonName(daemonName)
                .startDt(LocalDateTime.now())
                .status(DaemonLogStatus.RUNNING)
                .build();

        daemonLogMapper.save(daemonLog);
        return daemonLog.getSeqNo();
    }

    /**
     * 배치 종료 기록. 실패 건수에 따라 최종 상태를 SUCCESS/PARTIAL_FAIL/FAIL로 판정해 반영한다.
     * @param seqNo start()에서 받은 daemon_log seqNo
     * @param successCount 처리 성공 건수
     * @param failCount 처리 실패 건수
     * @param errMsg 실패 건별 에러 메시지 ([routine_seq_no] 메시지 형태로 개행 이어붙임, 없으면 null)
     */
    public void finish(Long seqNo, int successCount, int failCount, String errMsg) {
        DaemonLogStatus status;
        if (failCount == 0) {
            status = DaemonLogStatus.SUCCESS;
        } else if (successCount > 0) {
            status = DaemonLogStatus.PARTIAL_FAIL;
        } else {
            status = DaemonLogStatus.FAIL;
        }

        DaemonLog daemonLog = DaemonLog.builder()
                .seqNo(seqNo)
                .endDt(LocalDateTime.now())
                .successCount(successCount)
                .failCount(failCount)
                .status(status)
                .errMsg(errMsg)
                .build();

        daemonLogMapper.update(daemonLog);
    }
}
