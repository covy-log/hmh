package com.hmh.service;

import com.hmh.domain.DailyLog;
import com.hmh.domain.RoutineCycle;
import com.hmh.domain.constant.LogStatus;
import com.hmh.dto.Routine.RoutineCycleStatusDto;
import com.hmh.repository.DailyLogMapper;
import com.hmh.repository.RoutineCycleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineCycleService {

    private final RoutineCycleMapper routineCycleMapper;

    private final DailyLogMapper dailyLogMapper;

    public void save(RoutineCycle routinecycle) {

        routineCycleMapper.save(routinecycle);
    }

    public List<RoutineCycleStatusDto> findCurrentStatusByMemberSeqNo(Long memberSeqNo) {
        return routineCycleMapper.findCurrentStatusByMemberSeqNo(memberSeqNo);
    }

    /**
     * 사이클에 속한 daily_log 중 완료(DONE) 건들의 achievedValue를 전부 합산해서
     * routine_cycle.current_achieved_value에 재반영한다.
     * (증감식이 아니라 매번 재계산하는 방식 — 중복 클릭/수정 등에 안전)
     */
    public void recalculateAchievedValue(Long cycleSeqNo) {
        RoutineCycle cycle = routineCycleMapper.findById(cycleSeqNo)
                .orElseThrow(() -> new IllegalStateException("사이클을 찾을 수 없습니다."));

            BigDecimal totalAchieved = dailyLogMapper.findAllByCycleSeqNo(cycleSeqNo).stream()
                .filter(log -> log.getStatus() == LogStatus.DONE)
                .map(DailyLog::getAchievedValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cycle.setCurrentAchievedValue(totalAchieved);
        routineCycleMapper.update(cycle);
    }
}
