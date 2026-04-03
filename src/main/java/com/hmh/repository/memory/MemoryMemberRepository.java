package com.hmh.repository.memory;

import com.hmh.domain.Member;
import com.hmh.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    // 동시성 문제를 고려하여 ConcurrentHashMap 사용
    private static final Map<String, Member> store = new ConcurrentHashMap<>();

    // 고유 넘버 생성을 위한 AtomicLong (초기값 0)
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Member save(Member member) {

        // 시퀀스 값을 1 증가시키고 member 객체에 할당
        member.setSeqNo(sequence.incrementAndGet());

        store.put(member.getLoginId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Member member) {
        store.put(member.getLoginId(), member);
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}
