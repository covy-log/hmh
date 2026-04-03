package com.hmh.repository;

import com.hmh.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {

    /**
     * 회원 저장
     * @param member
     * @return
     */
    Member save(Member member);

    /**
     * 회원 조회
     * @param id
     * @return
     */
    Optional<Member> findById(String id);

    /**
     * 전체 회원 조회
     * @return
     */
    List<Member> findAll();

    /**
     * 회원 정보 변경(탈퇴포함)
     * @param member
     */
    void update(Member member);

    /**
     * 전체 회원 초기화(테스트 용)
     */
    void clearStore();
}
