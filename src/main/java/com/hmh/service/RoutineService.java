package com.hmh.service;

import com.hmh.domain.Routine;
import com.hmh.repository.DailyLogMapper;
import com.hmh.repository.RoutineCycleMapper;
import com.hmh.repository.RoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineMapper routineMapper;

    private final RoutineCycleMapper routineCycleMapper;

    private final DailyLogMapper dailyLogMapper;

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

    /**
     * 루틴 삭제 (소프트 삭제)
     * 루틴을 삭제 상태로 변경하고, 연관된 진행중 주간 사이클과 미수행(TODO) 일일 기록도 함께 취소한다.
     * 세 작업을 하나의 트랜잭션으로 묶어 일부만 반영되는 상황을 방지한다.
     * @param seqNo 루틴 고유번호
     * @param memberSeqNo 회원 고유번호 (본인 루틴만 삭제되도록 제한)
     */
    @Transactional
    public void delete(Long seqNo, Long memberSeqNo) {

        // 1. 루틴 소프트 삭제
        routineMapper.deleteBySeqNo(seqNo, memberSeqNo);

        // 2. 진행중 주간 사이클 취소
        routineCycleMapper.cancelByRoutineSeqNo(seqNo, memberSeqNo);

        // 3. 미수행(TODO) 일일 기록 취소
        dailyLogMapper.cancelByRoutineSeqNo(seqNo, memberSeqNo);
    }
}
