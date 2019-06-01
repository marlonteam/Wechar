package com.mazhe.util;

/**
 * @author wangsong
 * @date 17/04/2017
 */
public abstract class RandomUtils {
    public static String generateRandomCode(int length) {

        String output = "";

        for (int i = 0; i < length; i++) {
            output += generateRandomDigit();
        }

        return output;
    }

    public static String generateRandomDigit() {

        return String.valueOf(Double.valueOf(Math.floor(Math.random() * 9.0D + 1.0D)).intValue());
    }
}
