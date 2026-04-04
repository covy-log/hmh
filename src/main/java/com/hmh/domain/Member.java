package com.hmh.domain;

import com.hmh.domain.constant.MemberRole;
import com.hmh.domain.constant.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    /**
     * 회원 고유번호 (PK)
     */
    private Long seqNo;

    /**
     * 로그인 아이디
     */
    private String loginId;

    /**
     * 비밀번호 (DB 저장 시 반드시 암호화 필요)
     */
    private String password;

    /**
     * 사용자 닉네임 또는 이름
     */
    private String name;

    /**
     * 이메일 주소
     * (비밀번호 찾기, 데몬이 루틴 미달성 알림 메일을 보낼 때 등 확장을 위해 거의 필수야)
     */
    private String email;

    /**
     * 주시작요일
     */
    private String weekStartDay;

    /**
     * 회원 상태 (예: ACTIVE, DORMANCY(휴면), BANNED, DELETED)
     */
    @Builder.Default
    private MemberStatus status = MemberStatus.ACTIVE;

    /**
     * 회원 권한 (예: BASIC_USER, ADMIN)
     */
    @Builder.Default
    private MemberRole role = MemberRole.BASIC_USER;

    /**
     * 마지막 로그인 일시
     * (나중에 '1년 이상 미접속 시 휴면 계정 전환' 같은 데몬을 돌리려면 꼭 필요해)
     */
    private LocalDateTime lastLoginAt;

    /**
     * 계정 생성일시
     */
    private LocalDateTime createAt;

    /**
     * 계정 변경일시
     */
    private LocalDateTime changeAt;
}