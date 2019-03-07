package com.douguo.ndc.util;

/**
 * Created by lichang on 2018/8/7
 */
public class ProfileUtil {
    public static final String ALL = "all";
    public static final String SEX = "sex";
    public static final String INCOME = "income";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String GENERATION = "generation";
    public static final String PROFESSION = "profession";
    public static final String TABOOS_NAME = "taboos.name";
    public static final String CHRONICILLNESS_NAME = "chronicillness.name";
    public static final String TAGS_TAG = "interested_tags.tag";
    public static final String STATES_NAME = "states.name";
    public static final String TAGS_SEX="tags_sex";

    public static String converTitle(String type) {
        if (type.equals(ALL)) {
            return "全部";
        }else if (type.equals(SEX)) {
            return "性别";
        } else if (type.equals(INCOME)) {
            return "收入";
        } else if (type.equals(PROVINCE)) {
            return "省份";
        } else if (type.equals(CITY)) {
            return "城市";
        } else if (type.equals(GENERATION)) {
            return "年代";
        } else if (type.equals(PROFESSION)) {
            return "职业";
        } else if (type.equals(TABOOS_NAME)) {
            return "忌口";
        } else if (type.equals(CHRONICILLNESS_NAME)) {
            return "疾病";
        } else if (type.equals(TAGS_TAG)) {
            return "兴趣";
        } else if (type.equals(STATES_NAME)){
            return "膳食状态标签";
        } else {
            return "其他";
        }

    }
}
