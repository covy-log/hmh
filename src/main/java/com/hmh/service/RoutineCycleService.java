package com.hmh.service;

import com.hmh.domain.RoutineCycle;
import com.hmh.repository.RoutineCycleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutineCycleService {

    private final RoutineCycleMapper routineCycleMapper;

    public void save(RoutineCycle routinecycle) {

        routineCycleMapper.save(routinecycle);
    }
}
