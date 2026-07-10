package com.hmh.service;

import com.hmh.domain.DailyLog;
import com.hmh.dto.Routine.DailyLogDto;
import com.hmh.dto.Routine.DailyLogHistoryDto;
import com.hmh.repository.DailyLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyLogService {

    private final DailyLogMapper dailyLogMapper;

    public void save(DailyLog dailyLog) {

        dailyLogMapper.save(dailyLog);
    }

    public List<DailyLogDto> findAllOfTodayByDailyLog(DailyLog dailyLog) {
        return dailyLogMapper.findAllOfTodayByDailyLog(dailyLog);
    }

    public void update(DailyLog dailyLog) {

        dailyLogMapper.update(dailyLog);
    }

    public Optional<DailyLog> findById(Long seqNo) {
        return dailyLogMapper.findById(seqNo);
    }

    public List<DailyLogHistoryDto> findHistoryByMemberSeqNo(Long memberSeqNo) {
        return dailyLogMapper.findHistoryByMemberSeqNo(memberSeqNo);
    }
}

