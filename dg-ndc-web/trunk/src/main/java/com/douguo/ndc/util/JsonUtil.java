package com.douguo.ndc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by lichang on 2018/8/2
 */
public class JsonUtil {

    /**
     * json字符串转json对象
     * @param desc
     * @return
     */
    public static JSONObject parseStrToJsonObj (String desc){
        JSONObject descJsonObj = JSON.parseObject(desc);
        return descJsonObj ;
    }
}
