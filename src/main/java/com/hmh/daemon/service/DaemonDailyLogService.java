package com.hmh.daemon.service;

import com.hmh.repository.DailyLogMapper;
import com.hmh.repository.RoutineCycleMapper;
import com.hmh.repository.RoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DaemonDailyLogService {

    private final RoutineMapper routineMapper;
    private final RoutineCycleMapper routineCycleMapper;
    private final DailyLogMapper dailyLogMapper;


}
