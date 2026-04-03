package com.hmh.domain.constant;

public enum LogStatus {

    TODO ("미완료"),
    DONE ("완료"),
    FAIL ("실패");

    private String description;

    LogStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
