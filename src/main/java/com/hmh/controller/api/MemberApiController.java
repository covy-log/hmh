package com.hmh.controller.api;

import com.hmh.domain.Member;
import com.hmh.dto.member.MemberLoginDto;
import com.hmh.dto.member.MemberSginupDto;
import com.hmh.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입 API
     */
    @PostMapping("/join")
    public ResponseEntity<String> join(@Valid @RequestBody MemberSginupDto joinDto) {

        // 패스워드 암호화
        String encPwd = passwordEncoder.encode(joinDto.getPassword());

        // 1. DTO를 도메인 엔티티로 변환
        Member newMember = Member.builder()
                .loginId(joinDto.getLoginId())
                .password(encPwd)
                .name(joinDto.getName())
                .email(joinDto.getEmail())
                .weekStartDay(joinDto.getWeekStartDay())
                .build();

        try {
            // 2. 서비스 로직 호출 (중복 검사 및 저장)
            memberService.join(newMember);

            // 3. 성공 시 201 Created 상태 코드 반환
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");

        } catch (IllegalStateException e) {
            // 4. 중복 아이디인 경우 (MemberService의 validateDuplicateMember에서 던진 예외 처리)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody MemberLoginDto loginDto,
                                        HttpServletRequest request) {

        String loginId = loginDto.getLoginId();
        String encInputPwd = passwordEncoder.encode(loginDto.getPassword());

        Member member = memberService.login(loginId, encInputPwd);

        // 로그인 성공 처리 (세션 생성)
        // request.getSession(true): 세션이 있으면 있는 걸 반환하고, 없으면 신규 세션을 생성함
        HttpSession session = request.getSession(true);

        // 세션에 로그인 회원 정보 보관 (보통 상수나 enum으로 키값을 관리하는 게 좋아)
        session.setAttribute("LOGIN_MEMBER", member.getSeqNo()); // 보안상 전체 객체보단 PK나 ID만 저장하는 걸 권장해

        // 성공 시 200 OK 반환
        return ResponseEntity.ok("로그인 성공");
    }

    /**
     * 로그아웃 API
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // false: 세션이 없으면 새로 생성하지 않고 null을 반환
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 날리기 (로그아웃)
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}