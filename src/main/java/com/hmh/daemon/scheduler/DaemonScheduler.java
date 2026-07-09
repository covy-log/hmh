package com.hmh.daemon.scheduler;

import com.hmh.daemon.service.DaemonDailyLogService;
import com.hmh.daemon.service.DaemonRoutineCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DaemonScheduler {

    private final DaemonRoutineCycleService daemonRoutineCycleService;
    private final DaemonDailyLogService daemonDailyLogService;

    /**
     * 매일 00:00:01 실행. 루틴 사이클 생성이 끝난 뒤 일일 기록 생성이 이어지도록 순서를 보장한다.
     */
    @Scheduled(cron = "${daemon.cron.routine.cycle.daily}", zone = "Asia/Seoul")
    public void runDailyBatch() {
        daemonRoutineCycleService.createWeeklyCycle();
        daemonDailyLogService.createDailyLog();
    }
}
