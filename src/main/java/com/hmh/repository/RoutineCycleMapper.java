package com.hmh.repository;

import com.hmh.domain.RoutineCycle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoutineCycleMapper {

    /**
     * 루틴 사이클 저장
     * @param cycle
     * @return
     */
    void save(RoutineCycle cycle);

    /**
     * 루틴 사이클 조회
     * @param seqNo
     * @return
     */
    Optional<RoutineCycle> findById(Long seqNo);

    /**
     * 특정 루틴 모든 사이틀 이력 조회
     * @param routineSeqNo
     * @return
     */
    List<RoutineCycle> findAllByRoutineSeqNo(Long routineSeqNo);

    /**
     * 특정 루틴 가장 최근 사이클 조회
     * (데몬이 이 주기의 endYmd 가 지났는지 확인하고, 지났으면 새 주기를 생성하기 위함)
     * @param routineSeqNo
     * @return
     */
    Optional<RoutineCycle> findLatestByRoutineSeqNo(Long routineSeqNo);
}
