package com.hmh.domain.constant;

public enum LogStatus {

    TODO ("진행중"),
    DONE ("완료"),
    FAIL ("실패"),
    CANCEL ("취소");

    private String description;

    LogStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
