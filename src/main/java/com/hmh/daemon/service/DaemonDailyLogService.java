package com.hmh.daemon.service;

import com.hmh.domain.DailyLog;
import com.hmh.domain.Routine;
import com.hmh.domain.RoutineCycle;
import com.hmh.domain.constant.LogStatus;
import com.hmh.domain.constant.RoutineStatus;
import com.hmh.repository.DailyLogMapper;
import com.hmh.repository.RoutineCycleMapper;
import com.hmh.repository.RoutineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DaemonDailyLogService {

    private static final String DAEMON_NAME = "DailyLogDaemon";

    private final RoutineMapper routineMapper;
    private final RoutineCycleMapper routineCycleMapper;
    private final DailyLogMapper dailyLogMapper;
    private final DaemonLogService daemonLogService;

    public void createDailyLog() {
        Long daemonLogSeqNo = daemonLogService.start(DAEMON_NAME);
        int successCount = 0;
        int failCount = 0;
        StringBuilder errMsgBuilder = new StringBuilder();

        try {
            LocalDate today = LocalDate.now();
            String dayNum = String.valueOf(today.getDayOfWeek().getValue());

            // 과거 미완료 기록 FAIL 처리
            DailyLog dailyLogParam = new DailyLog();
            dailyLogParam.setTodoYmd(today);
            dailyLogParam.setStatus(LogStatus.TODO);
            dailyLogMapper.updateTodoToFail(dailyLogParam);

            Routine routineParam = new Routine();
            routineParam.setStatus(RoutineStatus.IN_PROGRESS);
            routineParam.setStartYmd(today);
            List<Routine> routineList = routineMapper.findAllByInProgress(routineParam);

            for (Routine routine : routineList) {

                // 오늘이 수행 요일일 경우
                if (routine.getDaysOfWeek().contains(dayNum)){

                    try {
                        Optional<RoutineCycle> currentCycle = routineCycleMapper.findLatestByRoutineSeqNo(routine.getSeqNo());

                        if (currentCycle.isEmpty()) {
                            failCount++;
                            errMsgBuilder.append("[").append(routine.getSeqNo()).append("] 진행 중인 사이클이 없어 DailyLog를 생성하지 못했습니다.\n");
                            log.warn("루틴(seqNo={})의 진행 중인 사이클이 없어 DailyLog를 생성하지 못했습니다.", routine.getSeqNo());
                            continue;
                        }

                        DailyLog dailyLog = DailyLog.builder()
                                .cycleSeqNo(currentCycle.get().getSeqNo())
                                .memberSeqNo(routine.getMemberSeqNo())
                                .todoYmd(today)
                                .build();

                        dailyLogMapper.save(dailyLog);
                        successCount++;

                    } catch (Exception e) {
                        failCount++;
                        errMsgBuilder.append("[").append(routine.getSeqNo()).append("] ").append(e.getMessage()).append("\n");
                        log.error("루틴(seqNo={}) DailyLog 생성 중 오류가 발생했습니다.", routine.getSeqNo(), e);
                    }
                }
            }
        } finally {
            daemonLogService.finish(daemonLogSeqNo, successCount, failCount,
                    errMsgBuilder.length() > 0 ? errMsgBuilder.toString() : null);
        }
    }
}
