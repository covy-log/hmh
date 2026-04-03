package com.hmh.repository.memory;

import com.hmh.domain.Routine;
import com.hmh.repository.RoutineRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryRoutineRepository implements RoutineRepository {

    // PK인 seqNo를 Key(Long)로 사용
    private static final Map<Long, Routine> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Routine save(Routine routine) {

        // 시퀀스 값을 1 증가시키고 member 객체에 할당
        routine.setSeqNo(sequence.incrementAndGet());

        store.put(routine.getSeqNo(), routine);
        return routine;
    }

    @Override
    public Optional<Routine> findBySeqNo(Long seqNo) {
        return Optional.ofNullable(store.get(seqNo));
    }

    @Override
    public List<Routine> findAllByMemberSeqNo(Long memberSeqNo) {
        // 저장소에 있는 모든 루틴을 꺼내서, 파라미터로 받은 memberSeqNo와 일치하는 것만 리스트로 반환
        return store.values().stream()
                .filter(routine -> routine.getMemberSeqNo().equals(memberSeqNo))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Routine routine) {
        store.put(routine.getSeqNo(), routine);
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}