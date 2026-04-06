package com.hmh.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoutineMapper {

    /**
     * 루틴 저장
     * @param routine
     * @return
     */
//    Routine save(Routine routine);

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
