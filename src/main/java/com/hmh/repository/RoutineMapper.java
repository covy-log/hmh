package com.hmh.repository;

import com.hmh.domain.Routine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoutineMapper {
    /**
     * 루틴 저장
     * @param routine
     * @return
     */
//    Routine save(Routine routine);

    /**
     * 특정 루틴 조회
     * @param seqNo
     * @return
     */
//    Optional<Routine> findBySeqNo(Long seqNo);

    /**
     * 특정 사용자 모든 루틴 조회
     * @param memberSeqNo
     * @return
     */
//    List<Routine> findAllByMemberSeqNo(Long memberSeqNo);

    /**
     * 루틴 변경(삭제포함)
     * @param routine
     */
//    void update(Routine routine);
}
