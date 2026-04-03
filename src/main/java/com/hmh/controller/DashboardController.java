package com.hmh.controller;

import com.hmh.domain.Member;
import com.hmh.domain.constant.MemberRole;
import com.hmh.domain.constant.MemberStatus;
import com.hmh.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor // final이 붙은 필드의 생성자를 자동으로 만들어줌(생성자 주입)
public class DashboardController {

    private final MemberRepository memberRepository;

    @GetMapping("/") // 첫 화면
    public String home(HttpSession session, Model model) {
        // 1. 세션에서 현재 사용자의 ID를 꺼내본다.
        String currentMemberId = (String) session.getAttribute("MEMBER_ID");

        // 2. 만약 없다면? (처음 방문한 사용자)
        if (currentMemberId == null) {
            // 절대 안 겹치는 랜덤한 고유 ID 생성 (임시 아이디)
            currentMemberId = UUID.randomUUID().toString();

            // 세션에 발급받은 ID를 저장 (브라우저를 닫기 전까지 유지됨)
            session.setAttribute("MEMBER_ID", currentMemberId);

            // MemoryMemberRepository에 이 임시 ID로 회원을 하나 쓱 만들어둔다.
            Member tempMember = Member.builder()
                    .loginId(currentMemberId) // String 타입이라고 가정
                    .name("User_" + currentMemberId.substring(0, 4))
                    .role(MemberRole.BASIC_USER)
                    .status(MemberStatus.ACTIVE)
                    .build();
            memberRepository.save(tempMember);
        }
        return "dashboard";
    }
}
