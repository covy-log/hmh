package com.hmh.domain.constant;

public enum RoutineType {

    CHECK ("체크", "체크형"),
    TIME ("시간", "시간"),
    COUNT ("회", "누적형"),
    KM ("km", "km");

    /** 수치 뒤에 붙는 단위 표기 (예: 5"회") — DailyLogDto.setUnit 에서 사용 */
    private String description;

    /** 화면에 노출하는 타입 이름 (예: "누적형") — 루틴 설정 목록의 타입 배지에서 사용.
     *  routineSetting.html 의 JS typeLabel() 매핑과 값을 반드시 일치시킬 것. */
    private String label;

    RoutineType(String description, String label) {
        this.description = description;
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }
}
