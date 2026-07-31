package com.hmh.domain.constant;

public enum DaemonLogStatus {

    RUNNING ("실행중"),
    SUCCESS ("성공"),
    PARTIAL_FAIL ("부분실패"),
    FAIL ("실패");

    private String description;

    DaemonLogStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
