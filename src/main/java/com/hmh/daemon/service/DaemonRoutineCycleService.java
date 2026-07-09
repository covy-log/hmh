package com.hmh.daemon.service;

import com.hmh.domain.Routine;
import com.hmh.domain.RoutineCycle;
import com.hmh.domain.constant.RoutineStatus;
import com.hmh.repository.RoutineCycleMapper;
import com.hmh.repository.RoutineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DaemonRoutineCycleService {

    private final RoutineMapper routineMapper;
    private final RoutineCycleMapper routineCycleMapper;

    /**
     * 주간 루틴 사이클 생성
     */
    public void createWeeklyCycle() {
        LocalDate today = LocalDate.now();

        if (today.getDayOfWeek() != DayOfWeek.MONDAY) {
            return;
        }

        Routine routineParam = new Routine();
        routineParam.setStatus(RoutineStatus.IN_PROGRESS);
        routineParam.setStartYmd(today);
        List<Routine> routineList = routineMapper.findAllByInProgress(routineParam);

        for (Routine routine : routineList) {
            try {
                Optional<RoutineCycle> latestCycle = routineCycleMapper.findLatestByRoutineSeqNo(routine.getSeqNo());
                int cycleNumber = latestCycle.map(cycle -> cycle.getCycleNumber() + 1).orElse(1);

                RoutineCycle newCycle = RoutineCycle.builder()
                        .routineSeqNo(routine.getSeqNo())
                        .memberSeqNo(routine.getMemberSeqNo())
                        .cycleNumber(cycleNumber)
                        .startYmd(today)
                        .endYmd(today.plusDays(6))
                        .targetValue(routine.getTargetValue())
                        .dailyLimit(routine.getDailyLimit())
                        .build();

                routineCycleMapper.save(newCycle);
            } catch (Exception e) {
                log.error("루틴(seqNo={}) 사이클 생성 중 오류가 발생했습니다.", routine.getSeqNo(), e);
            }
        }
    }
}
