package com.mazhe.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

@Slf4j
public abstract class LocalDateTimes {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parse(String dateString) {

        return parse(dateString, null, (ZoneId) null, null);
    }

    public static LocalDateTime parse(
        String dateString,
        String pattern) {

        return parse(dateString, pattern, (ZoneId) null, null);
    }

    public static LocalDateTime parse(
        String dateString,
        TimeZone zone) {

        return parse(dateString, null, zone.toZoneId(), null);
    }

    public static LocalDateTime parse(
        String dateString,
        ZoneId zone) {

        return parse(dateString, null, zone, null);
    }

    public static LocalDateTime parse(
        String dateString,
        String pattern,
        TimeZone zone) {

        return parse(dateString, pattern, zone.toZoneId(), null);
    }

    public static LocalDateTime parse(
        String dateString,
        String pattern,
        ZoneId zone) {

        return parse(dateString, pattern, zone, null);
    }

    public static LocalDateTime parse(
        String dateString,
        Locale locale) {

        return parse(dateString, null, (ZoneId) null, locale);
    }

    public static LocalDateTime parse(
        String dateString,
        String pattern,
        Locale locale) {

        return parse(dateString, pattern, (ZoneId) null, locale);
    }

    public static LocalDateTime parse(
        String dateString,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, null, zone.toZoneId(), locale);
    }

    public static LocalDateTime parse(
        String dateString,
        ZoneId zone,
        Locale locale) {

        return parse(dateString, null, zone, locale);
    }

    public static LocalDateTime parse(
        String dateString,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, pattern, zone.toZoneId(), locale);
    }

    public static LocalDateTime parse(
        String dateString,
        String pattern,
        ZoneId zone,
        Locale locale) {

        LocalDateTime date = null;

        if (Strings.isNullOrEmpty(dateString)) {
            return null;
        }

        try {
            date = LocalDateTime.parse(
                dateString,
                DateTimeFormatters.of(pattern, zone, locale, DEFAULT_PATTERN));
        } catch (Exception e) {
            log.error("Error occurred when parsing string '{}' to {}: {}",
                dateString, LocalDateTime.class.getName(), e.getMessage());
        }

        return date;
    }

    public static String format(LocalDateTime date) {

        return format(date, null, (ZoneId) null, null);
    }

    public static String format(
        LocalDateTime date,
        TimeZone zone) {

        return format(date, null, zone, null);
    }

    public static String format(
        LocalDateTime date,
        String pattern) {

        return format(date, pattern, (ZoneId) null, null);
    }

    public static String format(
        LocalDateTime date,
        String pattern,
        TimeZone zone) {

        return format(date, pattern, zone.toZoneId(), null);
    }

    public static String format(
        LocalDateTime date,
        Locale locale) {

        return format(date, null, (ZoneId) null, locale);
    }

    public static String format(
        LocalDateTime date,
        TimeZone zone,
        Locale locale) {

        return format(date, null, zone.toZoneId(), locale);
    }

    public static String format(
        LocalDateTime date,
        String pattern,
        Locale locale) {

        return format(date, pattern, (ZoneId) null, locale);
    }

    public static String format(
        LocalDateTime date,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return format(date, pattern, zone.toZoneId(), locale);
    }

    public static String format(
        LocalDateTime date,
        String pattern,
        ZoneId zone,
        Locale locale) {

        if (Objects.isNull(date)) {
            return null;
        }

        return DateTimeFormatters
            .of(pattern, zone, locale, DEFAULT_PATTERN)
            .format(date);
    }

    public static LocalDateTime getCurrentDate() {

        return getCurrentDate(Zones.getDefaultTimeZone());
    }

    public static LocalDateTime getCurrentDate(TimeZone zone) {

        return getCurrentDate(zone.toZoneId());
    }

    public static LocalDateTime getCurrentDate(ZoneId zone) {

        return LocalDateTime.now(zone);
    }

    public static LocalDateTime getCurrentUTCDate() {

        return getCurrentDate(ZoneId.of("UTC"));
    }
}
