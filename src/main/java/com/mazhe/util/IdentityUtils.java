package com.mazhe.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 身份证工具
 * @author: Yuhaihan
 * @create: 2019-04-12 15:47
 **/
public class IdentityUtils {

    private static final int IDENTITY_SIZE = 18;

    /**
     * 18位身份证截取生日（年-月-日）
     * @param card
     * @return
     */
    public static String getBirthday(String card){
        if (StringUtils.isEmpty(card)) {
            return null;
        }
        if (card.split("").length < IDENTITY_SIZE) {
            return null;
        }

        return new StringBuffer()
                .append(card.substring(6, 10))
                .append("-")
                .append(card.substring(10, 12))
                .append("-")
                .append(card.substring(12, 14))
                .toString();

    }


}
