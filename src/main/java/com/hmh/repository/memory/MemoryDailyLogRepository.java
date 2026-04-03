package com.hmh.repository.memory;

import com.hmh.domain.DailyLog;
import com.hmh.repository.DailyLogRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryDailyLogRepository implements DailyLogRepository {

    private static final Map<Long, DailyLog> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public DailyLog save(DailyLog dailyLog) {
        dailyLog.setSeqNo(sequence.incrementAndGet());
        store.put(dailyLog.getSeqNo(), dailyLog);
        return dailyLog;
    }

    @Override
    public Optional<DailyLog> findById(Long seqNo) {
        return Optional.ofNullable(store.get(seqNo));
    }

    @Override
    public List<DailyLog> findAllByMemberSeqNoAndTodoYmd(Long memberSeqNo, LocalDate todoYmd) {

        // 특정 회원의 특정 날짜(todoYmd)에 해당하는 기록만 필터링
        return store.values().stream()
                .filter(log -> log.getMemberSeqNo().equals(memberSeqNo)
                        && log.getTodoYmd().equals(todoYmd))
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyLog> findAllByCycleSeqNo(Long cycleSeqNo) {

        // 특정 사이클(cycleSeqNo)에 속한 기록만 필터링
        return store.values().stream()
                .filter(log -> log.getCycleSeqNo().equals(cycleSeqNo))
                .collect(Collectors.toList());
    }

    // 테스트를 위한 저장소 초기화 메서드
    public void clearStore() {
        store.clear();
    }
}