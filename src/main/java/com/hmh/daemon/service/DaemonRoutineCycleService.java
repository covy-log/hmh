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
                latestCycle.ifPresent(cycle -> closeCycleIfEnded(cycle, today));

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

    /**
     * 지난 사이클이 이미 끝났는데(endYmd가 지났는데) 아직 진행중 상태면, 목표 달성 여부에 따라 마감 처리한다.
     */
    private void closeCycleIfEnded(RoutineCycle cycle, LocalDate today) {
        if (cycle.getStatus() != RoutineStatus.IN_PROGRESS || !cycle.getEndYmd().isBefore(today)) {
            return;
        }

        RoutineStatus finalStatus = cycle.getCurrentAchievedValue().compareTo(cycle.getTargetValue()) >= 0
                ? RoutineStatus.SUCCESS
                : RoutineStatus.FAIL;
        cycle.setStatus(finalStatus);

        routineCycleMapper.update(cycle);
    }
}
