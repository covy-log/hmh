package com.hmh.service;

import com.hmh.domain.Routine;
import com.hmh.repository.RoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineMapper routineMapper;

    /**
     * 루틴 저장
     * @param routine
     * @return
     */
    public void save(Routine routine) {

        // 중복 루틴 확인
        routineMapper.save(routine);
    }

    public List<Routine> findAllByMemberSeqNo(Long memberSeqNo) {
        return routineMapper.findAllByMemberSeqNo(memberSeqNo);
    }
}
