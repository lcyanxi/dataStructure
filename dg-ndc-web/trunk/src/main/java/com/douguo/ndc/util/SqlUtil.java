package com.douguo.ndc.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/9/25
 */
public class SqlUtil {
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

}
