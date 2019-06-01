package com.mazhe.util;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Created by edison on 17/6/14.
 */
public abstract class Zones {
    public static TimeZone getDefaultTimeZone() {

        return TimeZone.getDefault();
    }

    public static ZoneId getDefaultZoneId() {

        return ZoneId.systemDefault();
    }

    public static int getDefaultTimeZoneOffset() {

        return getDefaultTimeZone().getRawOffset();
    }
}
