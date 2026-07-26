package com.hmh.repository;

import com.hmh.domain.DailyLog;
import com.hmh.dto.Routine.DailyLogDto;
import com.hmh.dto.Routine.DailyLogHistoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface DailyLogMapper {

    /**
     * 일일 기록 저장
     * @param dailyLog
     * @return
     */
    void save(DailyLog dailyLog);

    /**
     * 일일 기록 변경
     * @param dailyLog
     */
    void update(DailyLog dailyLog);

    /**
     * 일일 기록 단건 조회
     * @param seqNo
     * @return
     */
    Optional<DailyLog> findById(Long seqNo);

    /**
     * 어제 미완료 기록 FAIL 처리
     * @param dailyLog
     */
    void updateTodoToFail(DailyLog dailyLog);

    /**
     * 특정 사용자의 오늘 해야 할 루틴 목록 조회
     * @param dailyLog
     * @return
     */
    List<DailyLogDto> findAllOfTodayByDailyLog(DailyLog dailyLog);

    /**
     * 특정 사이클(예: 4월 1주차)에 속한 모든 일일 기록 조회 (통계용)
     * (이 리스트를 가져와서 achievedValue를 전부 SUM() 하면 해당 주기의 총 달성량이 나옴)
     * @param cycleSeqNo
     * @return
     */
    List<DailyLog> findAllByCycleSeqNo(Long cycleSeqNo);

    /**
     * 특정 사용자의 루틴 수행 히스토리 조회 (TODO 상태 제외)
     * @param memberSeqNo
     * @return
     */
    List<DailyLogHistoryDto> findHistoryByMemberSeqNo(Long memberSeqNo);

    /**
     * 특정 루틴에 속한 아직 수행하지 않은(TODO) 일일 기록을 취소(status = 'CANCEL') 처리
     * (루틴 삭제 시 남아있는 오늘/미래 할 일을 함께 정리하기 위함. 완료/실패 기록은 이력으로 보존)
     * @param routineSeqNo 루틴 고유번호
     * @param memberSeqNo 회원 고유번호 (본인 데이터만 처리되도록 제한)
     */
    void cancelByRoutineSeqNo(@Param("routineSeqNo") Long routineSeqNo, @Param("memberSeqNo") Long memberSeqNo);
}
