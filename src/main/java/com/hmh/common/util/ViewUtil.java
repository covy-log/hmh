package com.hmh.common.util;

import com.hmh.common.Const;
import com.hmh.domain.constant.RoutineType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewUtil {

    /**
     * 루틴 타입에 맞춰 수치를 view용 문자열로 변환 (예: TIME=90 -> "1시간 30분", KM=5000 -> "5.00km")
     */
    public static String convertValueView(RoutineType routineType, int value) {
        switch (routineType) {
            case TIME:
                return convertViewMmToHmStr(value);
            case KM:
                return convertViewMetersToKmStr(value);
            case CHECK:
            case COUNT:
            default:
                return value + Const.UNIT_COUNT;
        }
    }

    public static String convertViewToDays(String daysOfWeek) {
        if (daysOfWeek == null || daysOfWeek.trim().isEmpty()) {
            return "";
        }

        // 1. 공백 제거 및 정렬하여 하나의 문자열로 합치기 (예: " 5, 1,2 " -> "125")
        String normalizedStr = Arrays.stream(daysOfWeek.split(","))
                .map(String::trim)
                .sorted()
                .collect(Collectors.joining());

        // 2. 조건에 따른 특별한 문자열 리턴
        if ("1234567".equals(normalizedStr)) return "매일";
        if ("12345".equals(normalizedStr)) return "평일";
        if ("67".equals(normalizedStr)) return "주말";

        // 3. 위 조건에 해당하지 않는 경우 개별 요일로 매핑
        Map<String, String> dayMap = Map.of(
                "1", "월", "2", "화", "3", "수", "4", "목",
                "5", "금", "6", "토", "7", "일"
        );

        return Arrays.stream(daysOfWeek.split(","))
                .map(String::trim)
                .map(n -> dayMap.getOrDefault(n, n))
                .collect(Collectors.joining(", "));
    }

    public static String convertViewMmToHmStr(int mm) {

        StringBuilder sb = new StringBuilder();

        int hour = mm / 60;
        if (hour > 0) sb.append(hour + Const.UNIT_HOUR);

        int min = mm % 60;
        if (min > 0) sb.append(min + Const.UNIT_MINUTE);

        // 0분이면 위 두 조건이 모두 거짓이라 빈 문자열이 되어 화면에 아무것도 안 보인다 → "0분"으로 표기
        if (sb.length() == 0) sb.append(0).append(Const.UNIT_MINUTE);

        return sb.toString();
    }

    public static String convertViewMetersToKmStr (int meters){
        double kilometers = meters / 1000.0;

        return String.format("%.2f" + Const.UNIT_KM, kilometers);
    }
}
