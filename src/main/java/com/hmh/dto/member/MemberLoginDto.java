package com.hmh.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginDto {

    /**
     * 로그인 아이디
     * @NotBlank: null, "", " " 모두 허용하지 않음
     */
    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;

    /**
     * 비밀번호
     */
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}