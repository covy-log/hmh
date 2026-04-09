package com.hmh.service;

import com.hmh.domain.Member;
import com.hmh.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 로그인 로직
     */
    public Member login(String loginId, String loginPwd) {

        Member member = memberMapper.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        String encPwd = member.getPassword();
        boolean isMatch = passwordEncoder.matches(loginPwd, encPwd);

        if (isMatch) {
            memberMapper.updateLastLoginAt(member); // 로그인 시간 최신화
        } else {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        return member;
    }

    /**
     * 회원가입
     */
    public String join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberMapper.save(member);
        return member.getLoginId(); // 가입 후 로그인 ID 반환
    }

    /**
     * 중복 회원 검증 로직
     */
    private void validateDuplicateMember(Member member) {
        memberMapper.findById(member.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberMapper.findAll();
    }

    /**
     * 단일 회원 조회 (로그인 ID 기준)
     */
    public Optional<Member> findOne(String loginId) {
        return memberMapper.findById(loginId);
    }

    /**
     * 회원 정보 수정
     */
    public void updateMember(Member member) {
        // 필요하다면 여기서 수정하려는 회원이 실제로 존재하는지 먼저 검증할 수도 있어
        memberMapper.update(member);
    }
}