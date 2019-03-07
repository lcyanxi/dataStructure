package com.douguo.ndc.web;

import com.douguo.ndc.service.UserRetainService;
import com.douguo.ndc.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/16
 */
@Controller @RequestMapping("/user_retain") public class UserRetainController {

    @Autowired private UserRetainService userRetainService;

    @GetMapping(value = "/to_page") public String to_page() {
        return "pages/user_retain";
    }

    @GetMapping(value = "/queryData") @ResponseBody
    public String queryData(String appId, String date, String type, String timeType) {

        String startDate;
        String endDate;
        if (StringUtils.isBlank(date)) {
            startDate = DateUtil.getDate();
            endDate = DateUtil.getDate();
        } else {
            //2018-11-16 - 2018-11-16
            startDate = date.substring(0, 10);
            endDate = date.substring(13, date.length());

        }
        return userRetainService.queryUserRetain(appId, type, startDate, endDate, timeType);

    }
}
