package com.hmh.common.util;

public class StringUtil {

    public static String convertToDays(int dayNum) {

        String[] dayOfWeek = {"", "월", "화", "수", "목", "금", "토", "일"};

        return dayOfWeek[dayNum];
    }
}
