package com.mazhe.util;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by edison on 17/6/14.
 */
@Slf4j
public abstract class ZonedDateTimes {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static ZonedDateTime parse(String dateString) {

        return parse(dateString, null, (ZoneId) null, null);
    }

    public static ZonedDateTime parse(
        String dateString,
        String pattern) {

        return parse(dateString, pattern, (ZoneId) null, null);
    }

    public static ZonedDateTime parse(
        String dateString,
        TimeZone zone) {

        return parse(dateString, null, zone.toZoneId(), null);
    }

    public static ZonedDateTime parse(
        String dateString,
        ZoneId zone) {

        return parse(dateString, null, zone, null);
    }

    public static ZonedDateTime parse(
        String dateString,
        String pattern,
        TimeZone zone) {

        return parse(dateString, pattern, zone.toZoneId(), null);
    }


    public static ZonedDateTime parse(
        String dateString,
        String pattern,
        ZoneId zone) {

        return parse(dateString, pattern, zone, null);
    }

    public static ZonedDateTime parse(
        String dateString,
        Locale locale) {

        return parse(dateString, null, (ZoneId) null, locale);
    }

    public static ZonedDateTime parse(
        String dateString,
        String pattern,
        Locale locale) {

        return parse(dateString, pattern, (ZoneId) null, locale);
    }

    public static ZonedDateTime parse(
        String dateString,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, null, zone.toZoneId(), locale);
    }

    public static ZonedDateTime parse(
        String dateString,
        ZoneId zone,
        Locale locale) {

        return parse(dateString, null, zone, locale);
    }

    public static ZonedDateTime parse(
        String dateString,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, pattern, zone.toZoneId(), locale);
    }

    public static ZonedDateTime parse(
        String dateString,
        String pattern,
        ZoneId zone,
        Locale locale) {

        ZonedDateTime dateTime = null;

        try {
            dateTime = ZonedDateTime.parse(dateString, dateFormattter(pattern, zone, locale));
        } catch (Exception e) {
            log.error("Error occurred when parsing string '{}' to {}: {}",
                dateString, ZonedDateTime.class.getName(), e.getMessage());
        }

        return dateTime;
    }

    public static String format(ZonedDateTime date) {

        return format(date, DEFAULT_PATTERN, (ZoneId) null, null);
    }

    public static String format(
        ZonedDateTime date,
        TimeZone zone) {

        return format(date, DEFAULT_PATTERN, zone.toZoneId(), null);
    }

    public static String format(
        ZonedDateTime date,
        ZoneId zone) {

        return format(date, DEFAULT_PATTERN, zone, null);
    }

    public static String format(
        ZonedDateTime date,
        String pattern) {

        return format(date, pattern, (ZoneId) null, null);
    }

    public static String format(
        ZonedDateTime date,
        String pattern,
        TimeZone zone) {

        return format(date, pattern, zone.toZoneId(), null);
    }

    public static String format(
        ZonedDateTime date,
        String pattern,
        ZoneId zone) {

        return format(date, pattern, zone, null);
    }

    public static String format(
        ZonedDateTime date,
        Locale locale) {

        return format(date, DEFAULT_PATTERN, (ZoneId) null, locale);
    }

    public static String format(
        ZonedDateTime date,
        TimeZone zone,
        Locale locale) {

        return format(date, DEFAULT_PATTERN, zone.toZoneId(), locale);
    }

    public static String format(
        ZonedDateTime date,
        ZoneId zone,
        Locale locale) {

        return format(date, DEFAULT_PATTERN, zone, locale);
    }

    public static String format(
        ZonedDateTime dateTime,
        String pattern,
        Locale locale) {

        return format(dateTime, pattern, (ZoneId) null, locale);
    }

    public static String format(
        ZonedDateTime dateTime,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return format(dateTime, pattern, zone.toZoneId(), locale);
    }

    public static String format(
        ZonedDateTime dateTime,
        String pattern,
        ZoneId zone,
        Locale locale) {

        if (dateTime == null) {
            throw new IllegalArgumentException("Require zoned date time");
        }

        if (zone == null) {
            zone = ZoneId.systemDefault();
        }

        if (locale == null) {
            locale = Locale.getDefault();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, locale);
        dateTimeFormatter = dateTimeFormatter.withZone(zone);

        return dateTime.format(dateTimeFormatter);
    }


    public static ZonedDateTime getCurrentDateTime() {

        return getCurrentDateTime(Zones.getDefaultTimeZone());
    }

    public static ZonedDateTime getCurrentDateTime(ZoneId zone) {

        return ZonedDateTime.now(zone);
    }

    public static ZonedDateTime getCurrentDateTime(TimeZone zone) {

        return ZonedDateTime.now(zone.toZoneId());
    }

    public static ZonedDateTime getCurrentUTCDateTime() {

        return getCurrentDateTime(TimeZone.getTimeZone("UTC").toZoneId());
    }

    public static String getFormattedCurrentDateTime() {

        return getFormattedCurrentDateTime(Zones.getDefaultTimeZone());
    }

    public static String getFormattedCurrentDateTime(ZoneId timeZone) {

        return format(getCurrentDateTime(), timeZone);
    }

    public static String getFormattedCurrentDateTime(TimeZone zone) {

        return format(getCurrentDateTime(), zone.toZoneId());
    }

    public static String getFormattedCurrentUTCDateTime() {

        return getFormattedCurrentDateTime(TimeZone.getTimeZone("UTC").toZoneId());
    }

    public static Long getCurrentTime() {

        return getCurrentTime(Zones.getDefaultTimeZone());
    }

    public static Long getCurrentTime(ZoneId zone) {

        return ZonedDateTime.now(zone).toInstant().toEpochMilli();
    }

    public static Long getCurrentTime(TimeZone zone) {

        return ZonedDateTime.now(zone.toZoneId()).toInstant().toEpochMilli();
    }

    public static Long getCurrentUTCTime() {

        return getCurrentTime(TimeZone.getTimeZone("UTC").toZoneId());
    }

    public static Long diffDays(
        ZonedDateTime start,
        ZonedDateTime end) {

        return start.toLocalDate().until(end.toLocalDate(), ChronoUnit.DAYS);
    }

    private static DateTimeFormatter dateFormattter(
        String pattern,
        ZoneId zone,
        Locale locale) {

        return DateTimeFormatters.of(pattern, zone, locale, DEFAULT_PATTERN);
    }
}
