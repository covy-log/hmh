package com.hmh.domain.constant;

public enum RoutineType {

    CHECK ("체크형"),
    TIME ("누적형(분단위)"),
    COUNT ("누적형(횟수)"),
    KM ("누적형(km)");

    private String description;

    RoutineType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
