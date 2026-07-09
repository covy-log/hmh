package com.hmh.interceptor;

import com.hmh.common.Const;
import com.hmh.domain.Member;
import com.hmh.domain.constant.MemberRole;
import com.hmh.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminCheckInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        Long memberSeqNo = (session != null) ? (Long) session.getAttribute(Const.LOGIN_MEMBER) : null;

        boolean isAdmin = memberSeqNo != null
                && memberService.findBySeqNo(memberSeqNo)
                        .map(Member::getRole)
                        .map(role -> role == MemberRole.ADMIN)
                        .orElse(false);

        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}
