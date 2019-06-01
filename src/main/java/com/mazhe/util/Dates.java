package com.mazhe.util;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by edison on 17/6/14.
 */
@Slf4j
public abstract class Dates {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(String dateString) {

        return parse(dateString, null, (ZoneId) null, null);
    }

    public static Date parse(
        String dateString,
        String pattern) {

        return parse(dateString, pattern, (ZoneId) null, null);
    }

    public static Date parse(
        String dateString,
        TimeZone zone) {

        return parse(dateString, null, zone.toZoneId(), null);
    }

    public static Date parse(
        String dateString,
        ZoneId zone) {

        return parse(dateString, null, zone, null);
    }

    public static Date parse(
        String dateString,
        String pattern,
        TimeZone zone) {

        return parse(dateString, pattern, zone, null);
    }

    public static Date parse(
        String dateString,
        Locale locale) {

        return parse(dateString, null, (ZoneId) null, locale);
    }

    public static Date parse(
        String dateString,
        String pattern,
        Locale locale) {

        return parse(dateString, pattern, (ZoneId) null, locale);
    }

    public static Date parse(
        String dateString,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, null, zone.toZoneId(), locale);
    }

    public static Date parse(
        String dateString,
        ZoneId zone,
        Locale locale) {

        return parse(dateString, null, zone, locale);
    }

    public static Date parse(
        String dateString,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return parse(dateString, pattern, zone.toZoneId(), locale);
    }

    public static Date parse(
        String dateString,
        String pattern,
        ZoneId zone,
        Locale locale) {

        Date date = null;
        try {
            date = Date.from(
                ZonedDateTime.parse(
                    dateString,
                    DateTimeFormatters.of(
                        pattern, zone, locale, DEFAULT_PATTERN))
                    .toInstant());
        } catch (Exception e) {

            log.error("Error occurred when parsing string '{}' to {}: {}",
                dateString, Date.class.getName(), e.getMessage());
        }

        return date;
    }

    public static String format(Calendar calendar) {

        return format(calendar, null, (ZoneId) null, null);
    }

    public static String format(
        Calendar calendar,
        TimeZone zone) {

        return format(calendar, null, zone.toZoneId(), null);
    }

    public static String format(
        Calendar calendar,
        ZoneId zone) {

        return format(calendar, null, zone, null);
    }

    public static String format(
        Calendar calendar,
        String pattern) {

        return format(calendar, pattern, (ZoneId) null, null);
    }

    public static String format(
        Calendar calendar,
        String pattern,
        TimeZone zone) {

        return format(calendar.getTime(), pattern, zone.toZoneId(), null);
    }

    public static String format(
        Calendar calendar,
        String pattern,
        ZoneId zone) {

        return format(calendar.getTime(), pattern, zone, null);
    }

    public static String format(
        Calendar calendar,
        Locale locale) {

        return format(calendar, null, (ZoneId) null, locale);
    }

    public static String format(
        Calendar calendar,
        String pattern,
        Locale locale) {

        return format(calendar, pattern, (ZoneId) null, locale);
    }

    public static String format(
        Calendar calendar,
        TimeZone zone,
        Locale locale) {

        return format(calendar, null, zone.toZoneId(), locale);
    }

    public static String format(
        Calendar calendar,
        ZoneId zone,
        Locale locale) {

        return format(calendar, null, zone, locale);
    }

    public static String format(
        Calendar calendar,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return format(calendar.getTime(), pattern, zone.toZoneId(), locale);
    }

    public static String format(
        Calendar calendar,
        String pattern,
        ZoneId zone,
        Locale locale) {

        return format(calendar.getTime(), pattern, zone, locale);
    }

    public static String format(Date date) {

        return format(date, null, (ZoneId) null, null);
    }

    public static String format(
        Date date,
        TimeZone zone) {

        return format(date, null, zone.toZoneId(), null);
    }

    public static String format(
        Date date,
        ZoneId zone) {

        return format(date, null, zone, null);
    }

    public static String format(Date date, String pattern) {

        return format(date, pattern, (ZoneId) null, null);
    }

    public static String format(
        Date date,
        String pattern,
        TimeZone zone) {

        return format(date, pattern, zone.toZoneId(), null);
    }

    public static String format(
        Date date,
        String pattern,
        ZoneId zone) {

        return format(date, pattern, zone, null);
    }

    public static String format(
        Date date,
        Locale locale) {

        return format(date, null, (ZoneId) null, locale);
    }

    public static String format(
        Date date,
        TimeZone zone,
        Locale locale) {

        return format(date, null, zone.toZoneId(), locale);
    }

    public static String format(
        Date date,
        ZoneId zone,
        Locale locale) {

        return format(date, null, zone, locale);
    }

    public static String format(
        Date date,
        String pattern,
        Locale locale) {

        return format(date, pattern, (ZoneId) null, locale);
    }

    public static String format(
        Date date,
        String pattern,
        TimeZone zone,
        Locale locale) {

        return format(date, pattern, zone.toZoneId(), locale);
    }

    public static String format(Date date, String pattern, ZoneId zone, Locale locale) {
        if (zone == null) {
            zone = ZoneId.systemDefault();
        }

        return ZonedDateTime
            .ofInstant(date.toInstant(), zone)
            .format(DateTimeFormatters.of(pattern, zone, locale, DEFAULT_PATTERN));
    }

    public static Date getCurrentDateTime() {

        return getCurrentDateTime(Zones.getDefaultTimeZone());
    }


    public static Date getCurrentDateTime(TimeZone zone) {

        Calendar calendar = Calendar.getInstance(zone);
        calendar.setTimeInMillis(calendar.getTimeInMillis() + zone.getRawOffset()
            - Zones.getDefaultTimeZoneOffset());
        return calendar.getTime();
    }

    public static Date getCurrentDateTime(ZoneId zone) {

        return getCurrentDateTime(TimeZone.getTimeZone(zone.getId()));
    }

    public static Date getCurrentUTCDateTime() {

        return getCurrentDateTime(TimeZone.getTimeZone("UTC"));
    }

    public static String getFormattedCurrentDateTime() {

        return getFormattedCurrentDateTime(Zones.getDefaultTimeZone());
    }

    public static String getFormattedCurrentDateTime(TimeZone zone) {

        return format(getCurrentDateTime(), zone.toZoneId());
    }

    public static String getFormattedCurrentDateTime(ZoneId zone) {

        return format(getCurrentDateTime(), zone);
    }

    public static String getFormattedCurrentUTCDateTime() {

        return getFormattedCurrentDateTime(TimeZone.getTimeZone("UTC"));
    }

    public static Long getCurrentTime() {

        return getCurrentTime(Zones.getDefaultTimeZone());
    }

    public static Long getCurrentTime(TimeZone timeZone) {

        Calendar calendar = Calendar.getInstance(timeZone);
        return calendar.getTimeInMillis() + timeZone.getRawOffset() - Zones.getDefaultTimeZoneOffset();
    }

    public static Long getCurrentTime(ZoneId zone) {

        return getCurrentTime(TimeZone.getTimeZone(zone.getId()));
    }

    public static Long getCurrentUTCTime() {

        return getCurrentTime(TimeZone.getTimeZone("UTC"));
    }
}
