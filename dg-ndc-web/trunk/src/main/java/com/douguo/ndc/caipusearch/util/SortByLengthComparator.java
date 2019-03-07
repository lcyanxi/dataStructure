package com.douguo.ndc.caipusearch.util;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/24
 */
public class SortByLengthComparator implements Comparator<String> {
    @Override public int compare(String var1, String var2) {
        if (var1.length() < var2.length()) {
            return 1;
        } else if (var1.length() == var2.length()) {
            return 0;
        } else {
            return -1;
        }
    }
}
