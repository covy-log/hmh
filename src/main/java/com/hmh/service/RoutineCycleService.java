package com.hmh.service;

import com.hmh.domain.RoutineCycle;
import com.hmh.dto.Routine.RoutineCycleStatusDto;
import com.hmh.repository.RoutineCycleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineCycleService {

    private final RoutineCycleMapper routineCycleMapper;

    public void save(RoutineCycle routinecycle) {

        routineCycleMapper.save(routinecycle);
    }

    public List<RoutineCycleStatusDto> findCurrentStatusByMemberSeqNo(Long memberSeqNo) {
        return routineCycleMapper.findCurrentStatusByMemberSeqNo(memberSeqNo);
    }
}
