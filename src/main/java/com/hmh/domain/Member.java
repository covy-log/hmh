package com.hmh.domain;

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
    private Long seqNo;           // 시스템에서 관리하는 PK (시퀀스 역할)
    private String loginId;    // 사용자가 로그인할 때 쓰는 아이디
    private String password;   // 비밀번호
    private String name;       // 사용자 이름(별명)
    private LocalDateTime regDt; // 계정 생성일
    private LocalDateTime changeDt; // 변경일
    private int status; // 상태 (0:비활성화, 1:활성화)
}
