package com.hmh.repository;

import com.hmh.domain.DailyLog;
import com.hmh.dto.Routine.DailyLogDto;
import org.apache.ibatis.annotations.Mapper;

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
}
