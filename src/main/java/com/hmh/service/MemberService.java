package com.hmh.service;

import com.hmh.domain.Member;
import com.hmh.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    /**
     * 로그인 로직 (MemberService 내부에 추가)
     */
    public Member login(String loginId, String password) {
        // 아이디로 회원을 찾고, 비밀번호가 일치하면 해당 회원 객체를 반환

        // todo 성공시 last_login_at 마지막 로그인 시간 변경하기
        return memberMapper.findById(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null); // 실패 시 null 반환
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