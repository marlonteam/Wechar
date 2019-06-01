package com.mazhe.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Created by edison on 17/6/14.
 */
public abstract class YearMonths {
    public static final DateTimeFormatter DEFAULT_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM");

    public static String format(YearMonth yearMonth) {

        if (Objects.isNull(yearMonth)) {
            return "";
        }
        return yearMonth.format(DEFAULT_PATTERN);
    }

    public static YearMonth parse(String yearMonth) {

        if (Strings.isEmpty(yearMonth)) {
            return null;
        }

        return YearMonth.parse(yearMonth, DEFAULT_PATTERN);
    }
}
