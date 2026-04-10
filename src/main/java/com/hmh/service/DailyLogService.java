package com.hmh.service;

import com.hmh.domain.DailyLog;
import com.hmh.dto.Routine.DailyLogDto;
import com.hmh.repository.DailyLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
