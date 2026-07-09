package com.hmh.service;

import com.hmh.domain.Member;
import com.hmh.domain.constant.MemberProvider;
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
     * 단일 회원 조회 (PK 기준)
     */
    public Optional<Member> findBySeqNo(Long seqNo) {
        return memberMapper.findBySeqNo(seqNo);
    }

    /**
     * 회원 정보 수정
     */
    public void updateMember(Member member) {
        // 필요하다면 여기서 수정하려는 회원이 실제로 존재하는지 먼저 검증할 수도 있어
        memberMapper.update(member);
    }

    /**
     * 소셜 로그인 회원 조회, 없으면 신규 가입 처리
     * (loginId 기준으로 기존 회원을 찾고, 없으면 provider 기준으로 새로 생성)
     * loginId는 provider별로 고유하게 만들어서 넘겨줘야 함 (예: 구글은 email, 카카오는 "kakao_" + 카카오id)
     */
    public Member findOrCreateByOAuth(String loginId, String name, String email, MemberProvider provider) {
        Member member = memberMapper.findById(loginId)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .loginId(loginId)
                            .password(null)
                            .name(name)
                            .email(email)
                            .weekStartDay("1")
                            .provider(provider)
                            .build();
                    memberMapper.save(newMember);
                    return newMember;
                });

        memberMapper.updateLastLoginAt(member); // 로그인 시간 최신화
        return member;
    }
}