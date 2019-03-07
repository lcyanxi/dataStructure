package com.douguo.ndc.recommend;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.util.HbaseJavaClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2019/1/23
 */
@Controller @RequestMapping(value = "/recommend") public class RecommendController {

    private final static String TABLENAME = "recommend_online";
    private final static String FAMILYNAME = "behavior";
    private final static String COLUMNNAME = "search_caipu";

    @GetMapping(value = "/getData") @ResponseBody public String getData(String imei) {
        System.out.println("imei:" + imei);
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        try {
            List list = HbaseJavaClientUtil.getResultByColumn(TABLENAME, imei, FAMILYNAME, COLUMNNAME);
            if (!list.isEmpty()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data", list);
                return jsonObject.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
