package com.hmh.domain.constant;

public enum RoutineStatus {
    IN_PROGRESS ("진행중"),
    SUCCESS ("성공"),
    FAIL ("실패"),
    DELETE ("삭제"),
    CANCEL ("취소");

    private String description;

    RoutineStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
