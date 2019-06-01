package com.mazhe.util;

import java.math.BigDecimal;

/**
 * BigDecimal工具类
 *
 * @author: Yuhaihan
 * @create: 2019-04-17 14:56
 **/
public class BigDecimalUtil {
    private BigDecimalUtil() {
    }

    /**
     * BigDecimal 加法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * BigDecimal 减法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * BigDecimal 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * BigDecimal 除法 && 四舍五入,保留2位小数
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal divide(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);

    }


}
