package com.hmh.repository.memory;

import com.hmh.domain.RoutineCycle;
import com.hmh.repository.RoutineCycleRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryRoutineCycleRepository implements RoutineCycleRepository {

    private static final Map<Long, RoutineCycle> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public RoutineCycle save(RoutineCycle cycle) {
        cycle.setSeqNo(sequence.incrementAndGet());
        store.put(cycle.getSeqNo(), cycle);
        return cycle;
    }

    @Override
    public Optional<RoutineCycle> findById(Long seqNo) {
        return Optional.ofNullable(store.get(seqNo));
    }

    @Override
    public List<RoutineCycle> findAllByRoutineSeqNo(Long routineSeqNo) {
        return store.values().stream()
                .filter(cycle -> cycle.getRoutineSeqNo().equals(routineSeqNo))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoutineCycle> findLatestByRoutineSeqNo(Long routineSeqNo) {
        // 특정 루틴(routineSeqNo)을 가진 사이클들을 필터링한 후,
        // seqNo가 가장 큰(최신) 사이클을 찾아 Optional로 반환
        return store.values().stream()
                .filter(cycle -> cycle.getRoutineSeqNo().equals(routineSeqNo))
                .max(Comparator.comparing(RoutineCycle::getSeqNo));
    }

    public void clearStore() {
        store.clear();
    }
}