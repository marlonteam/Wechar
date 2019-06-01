package com.mazhe.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by edison on 17/6/14.
 */
public abstract class DateTimeFormatters {
    public static DateTimeFormatter of(
        String pattern,
        ZoneId zone,
        Locale locale,
        String defaultPattern) {

        if (zone == null) {
            zone = ZoneId.systemDefault();
        }

        if (Objects.isNull(pattern)) {
            pattern = defaultPattern;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);

        if (zone != null) {
            dateFormat.withZone(zone);
        }

        if (locale != null) {
            dateFormat.withLocale(locale);
        }

        return dateFormat;
    }
}
