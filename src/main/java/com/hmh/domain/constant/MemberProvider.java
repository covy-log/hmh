package com.hmh.domain.constant;

public enum MemberProvider {
    LOCAL("자체 가입"),
    GOOGLE("구글"),
    KAKAO("카카오"),
    NAVER("네이버");

    private final String description;

    MemberProvider(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
