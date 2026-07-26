package com.hmh.repository;

import com.hmh.domain.RoutineCycle;
import com.hmh.dto.Routine.RoutineCycleStatusDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 루틴 사이클 정보 변경 (달성 수치, 마감 상태 등)
     * @param cycle
     */
    void update(RoutineCycle cycle);

    /**
     * 특정 사용자의 진행 중인 루틴 사이클 달성 현황 조회
     * @param memberSeqNo
     * @return
     */
    List<RoutineCycleStatusDto> findCurrentStatusByMemberSeqNo(Long memberSeqNo);

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

    /**
     * 일일 루틴(daily_log) 변경 결과를 반영해 해당 사이클의 누적 달성 수치를 재계산
     * (DONE 상태인 daily_log의 achieved_value 합계로 덮어씀)
     * @param dailyLogSeqNo 방금 변경된 daily_log의 seqNo
     */
    void recalculateAchievedValueByDailyLogSeqNo(Long dailyLogSeqNo);

    /**
     * 특정 루틴의 진행중 사이클을 취소(status = 'CANCEL') 처리
     * (루틴 삭제 시 주간 사이클을 함께 정리하기 위함)
     * @param routineSeqNo 루틴 고유번호
     * @param memberSeqNo 회원 고유번호 (본인 데이터만 처리되도록 제한)
     */
    void cancelByRoutineSeqNo(@Param("routineSeqNo") Long routineSeqNo, @Param("memberSeqNo") Long memberSeqNo);
}
