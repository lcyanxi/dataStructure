package com.douguo.ndc.util;


import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/9/19
 */
public class BigDecimalUtil {

    public static  double deciMal(int top, int below) {
        double result = new BigDecimal((float)top / below).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
}
