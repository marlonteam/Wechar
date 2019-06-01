package com.mazhe.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by edison on 17/6/14.
 */
@Slf4j
public abstract class LocalDates {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static LocalDate parse(String dateString) {

        return parse(dateString, null, (ZoneId) null, null);
    }

    public static LocalDate parse(
        String dateString,
        String pattern) {

        return parse(dateString, pattern, (ZoneId) null, null);
    }

    public static LocalDate parse(
        String dateString,
        TimeZone zone) {

        return parse(dateString, null, zone.toZoneId(), null);
    }

    public static LocalDate parse(
        String dateString,
        ZoneId zone) {

        return parse(dateString, null, zone, null);
    }

    public static LocalDate parse(
        String dateString,
        String pattern,
        TimeZone zone) {

        return parse(dateString, pattern, zone.toZoneId(), null);
    }

    public static LocalDate parse(
        String dateString,
        String pattern,
        ZoneId zone) {

        return parse(dateString, pattern, zone, null);
    }

    public static LocalDate parse(
        String dateString,
        Locale locale) {

        return parse(dateString, null, (ZoneId) null, locale);
    }

    public static LocalDate parse(
        String dateString,
        String pattern,
        Locale locale) {

        return parse(dateString, pattern, (ZoneId) null, locale);
    }

    public static LocalDate parse(
        String dateString,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, null, zone.toZoneId(), locale);
    }

    public static LocalDate parse(
        String dateString,
        ZoneId zone,
        Locale locale) {

        return parse(dateString, null, zone, locale);
    }

    public static LocalDate parse(
        String dateString,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, pattern, zone.toZoneId(), locale);
    }

    public static LocalDate parse(
        String dateString,
        String pattern,
        ZoneId zone,
        Locale locale) {

        LocalDate date = null;

        try {
            date = LocalDate.parse(
                dateString,
                DateTimeFormatters.of(pattern, zone, locale, DEFAULT_PATTERN));
        } catch (Exception e) {
            log.error("Error occurred when parsing string '{}' to {}: {}",
                dateString, LocalDate.class.getName(), e.getMessage());
        }

        return date;
    }

    public static String format(LocalDate date) {

        return format(date, null, (ZoneId) null, null);
    }

    public static String format(
        LocalDate date,
        TimeZone zone) {

        return format(date, null, zone, null);
    }

    public static String format(
        LocalDate date,
        String pattern) {

        return format(date, pattern, (ZoneId) null, null);
    }

    public static String format(
        LocalDate date,
        String pattern,
        TimeZone zone) {

        return format(date, pattern, zone.toZoneId(), null);
    }

    public static String format(
        LocalDate date,
        Locale locale) {

        return format(date, null, (ZoneId) null, locale);
    }

    public static String format(
        LocalDate date,
        TimeZone zone,
        Locale locale) {

        return format(date, null, zone.toZoneId(), locale);
    }

    public static String format(
        LocalDate date,
        String pattern,
        Locale locale) {

        return format(date, pattern, (ZoneId) null, locale);
    }

    public static String format(
        LocalDate date,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return format(date, pattern, zone.toZoneId(), locale);
    }

    public static String format(
        LocalDate date,
        String pattern,
        ZoneId zone,
        Locale locale) {

        return DateTimeFormatters
            .of(pattern, zone, locale, DEFAULT_PATTERN)
            .format(date);
    }

    public static LocalDate getCurrentDate() {

        return getCurrentDate(Zones.getDefaultTimeZone());
    }

    public static LocalDate getCurrentDate(TimeZone zone) {

        return getCurrentDate(zone.toZoneId());
    }

    public static LocalDate getCurrentDate(ZoneId zone) {

        return LocalDate.now(zone);
    }

    public static LocalDate getCurrentUTCDate() {

        return getCurrentDate(ZoneId.of("UTC"));
    }
}
