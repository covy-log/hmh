package com.hmh.domain.constant;

public enum RoutineType {

    CHECK ("체크"),
    TIME ("시간"),
    COUNT ("회"),
    KM ("km");

    private String description;

    RoutineType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
