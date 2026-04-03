package com.hmh.domain.constant;

public enum MemberStatus {
    ACTIVE("활성"),
    DORMANCY("휴면"),
    DELETED("탈퇴"),
    BANNED("정지");

    private final String description;

    MemberStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
