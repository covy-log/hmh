package com.hmh.repository;

import com.hmh.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper // 스프링한테 "이건 MyBatis 매퍼야!" 라고 알려주는 어노테이션
public interface MemberMapper {

    /**
     * 회원 저장
     * @param member
     * @return
     */
    void save(Member member);

    /**
     * 회원 조회
     * @param id
     * @return
     */
    Optional<Member> findById(String loginId);

    /**
     * 전체 회원 조회
     * @return
     */
    List<Member> findAll();

    /**
     * 마지막 로그인 최신화
     * @param member
     */
    void updateLastLoginAt(Member member);

    /**
     * 회원 정보 변경(탈퇴포함)
     * @param member
     */
    void update(Member member);
}