package com.douguo.ndc.caipusearch.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/25
 */
public class SearchTypeUtil {
    private static final String KEYWORD="k";
    private static final String CATEGORIES = "t";

    public static String getSearchType(String q){
        if (StringUtils.isBlank(q)){
            return "";
        }else if(q.equals(KEYWORD)){
            return "keyword";
        }else if (q.equals(CATEGORIES)){
            return "categories";
        }else {
            return "ingredients";
        }
    }
}
