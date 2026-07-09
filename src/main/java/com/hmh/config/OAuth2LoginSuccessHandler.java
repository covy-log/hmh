package com.hmh.config;

import com.hmh.common.Const;
import com.hmh.domain.Member;
import com.hmh.domain.constant.MemberProvider;
import com.hmh.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2Token.getPrincipal();
        String registrationId = oAuth2Token.getAuthorizedClientRegistrationId();

        Member member = switch (registrationId) {
            case "kakao" -> loginWithKakao(oAuth2User);
            default -> loginWithGoogle(oAuth2User);
        };

        HttpSession session = request.getSession(true);
        session.setAttribute(Const.LOGIN_MEMBER, member.getSeqNo());

        response.sendRedirect("/dailyLog");
    }

    private Member loginWithGoogle(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        return memberService.findOrCreateByOAuth(email, name, email, MemberProvider.GOOGLE);
    }

    /**
     * 카카오는 닉네임만 동의받고, 이메일은 형식상 채우는 용도라 카카오 고유 id로 만든 더미값을 사용한다.
     * 응답 구조: { id, kakao_account: { profile: { nickname } } }
     */
    private Member loginWithKakao(OAuth2User oAuth2User) {
        String kakaoId = oAuth2User.getName(); // provider.kakao.user-name-attribute=id 로 설정됨

        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (kakaoAccount != null) ? (Map<String, Object>) kakaoAccount.get("profile") : null;
        String nickname = (profile != null) ? (String) profile.get("nickname") : ("카카오사용자" + kakaoId);

        String loginId = "kakao_" + kakaoId;
        String dummyEmail = loginId + "@kakao.local";

        return memberService.findOrCreateByOAuth(loginId, nickname, dummyEmail, MemberProvider.KAKAO);
    }
}
