package com.hmh.repository;

import com.hmh.domain.Routine;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository {

    /**
     * 루틴 저장
     * @param routine
     * @return
     */
    Routine save(Routine routine);

    /**
     * 특정 루틴 조회
     * @param seqNo
     * @return
     */
    Optional<Routine> findBySeqNo(Long seqNo);

    /**
     * 특정 사용자 모든 루틴 조회
     * @param memberSeqNo
     * @return
     */
    List<Routine> findAllByMemberSeqNo(Long memberSeqNo);

    /**
     * 루틴 변경(삭제포함)
     * @param routine
     */
    void update(Routine routine);

    /**
     * 전체 루틴 초기화(테스트 용)
     */
    void clearStore();
}
