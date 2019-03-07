package com.douguo.ndc.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/27
 */
public class Util {

    public static String showSQL(String sql, Object[] objs) {
        for (Object obj : objs) {
            if (obj == null){
                obj = "";
            }
            sql = sql.replaceFirst("\\?", "'" + obj.toString() + "'");
        }
        System.out.println(sql);
        return sql;
    }

    public static int getInt(Object value, int defaultVal) {
        if (value == null || value.equals("")) {
            return defaultVal;
        }
        return Integer.parseInt(value.toString());
    }

    public static long getLong(Object value, long defaultVal) {
        if (value == null || value.equals("")) {
            return defaultVal;
        }
        return Long.parseLong(value.toString());
    }
}