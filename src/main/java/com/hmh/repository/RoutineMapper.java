package com.hmh.repository;

import com.hmh.domain.Routine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoutineMapper {

    /**
     * 루틴 저장
     * @param routine
     * @return
     */
    void save(Routine routine);

    /**
     * 특정 사용자 모든 루틴 조회
     * @param memberSeqNo
     * @return
     */
    List<Routine> findAllByMemberSeqNo(Long memberSeqNo);

    /**
     * 진행중인 모든 루틴 조회
     * @param routine
     * @return
     */
    List<Routine> findAllByInProgress(Routine routine);

    /**
     * 루틴 변경(삭제포함)
     * @param routine
     */
    void update(Routine routine);

    /**
     * 루틴 소프트 삭제 (status = 'DELETE')
     * @param seqNo 루틴 고유번호
     * @param memberSeqNo 회원 고유번호 (본인 루틴만 삭제되도록 제한)
     */
    void deleteBySeqNo(@Param("seqNo") Long seqNo, @Param("memberSeqNo") Long memberSeqNo);
}
