package com.douguo.ndc.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.douguo.ndc.service.EventService;
import com.douguo.ndc.util.ConstantStr;
import com.douguo.ndc.util.QueryTableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/12
 */
@Controller @RequestMapping("/events") public class EventsController {

    @Autowired private EventService eventService;

    @GetMapping(value = "/events_next_page")
    public String toNextPage() {
        return "pages/events_next";
    }

    @RequestMapping(value = "/queryData", method = RequestMethod.GET) @ResponseBody
    public String queryData(@RequestParam String aoData,String date,String appId) {
        Map map = QueryTableUtil.getQueryTableParms(aoData);
        String sSearch = (String)map.get(ConstantStr.SSEARCH);

        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj =eventService.querySearchData(ConstantStr.PARAM_TYPE_ALL,appId,date,"","",sSearch);
        } else {
            getObj = eventService
                .queryData(ConstantStr.PARAM_TYPE_ALL,appId, date,"","", (Integer)map.get(ConstantStr.IDISPLAYSTART),
                    (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }

        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        //解决fastJson key值为null数据丢失问题
        return JSONObject.toJSONString(getObj, SerializerFeature.WriteMapNullValue);

    }

   @GetMapping(value = "/queryNextData")
   @ResponseBody
    public String queryNextData(@RequestParam String aoData,String date,String appId,String eventCode) {
        Map map = QueryTableUtil.getQueryTableParms(aoData);
       String sSearch = (String)map.get(ConstantStr.SSEARCH);

       JSONObject getObj;

       if (StringUtils.isNotBlank(sSearch)) {
           getObj =eventService.querySearchData(ConstantStr.PARAM_TYPE_KEY,appId,date,eventCode,"",sSearch);
       } else {
           getObj = eventService
               .queryData(ConstantStr.PARAM_TYPE_KEY,appId,date,eventCode,"", (Integer)map.get(ConstantStr.IDISPLAYSTART),
                   (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
       }
        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        //解决fastJson key值为null数据丢失问题
        return JSONObject.toJSONString(getObj, SerializerFeature.WriteMapNullValue);

    }

    @GetMapping(value = "/queryNextSubData")
    @ResponseBody
    public String queryNextSubData(@RequestParam String aoData,String date,String appId,String eventCode,String paramKey) {
        Map map = QueryTableUtil.getQueryTableParms(aoData);

        String sSearch = (String)map.get(ConstantStr.SSEARCH);

        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj =eventService.querySearchData(ConstantStr.PARAM_TYPE_VALUE,appId,date,eventCode,paramKey,sSearch);
        } else {
            getObj = eventService
                .queryData(ConstantStr.PARAM_TYPE_VALUE,appId,date,eventCode,paramKey, (Integer)map.get(ConstantStr.IDISPLAYSTART),
                    (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }

        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        //解决fastJson key值为null数据丢失问题
        return JSONObject.toJSONString(getObj, SerializerFeature.WriteMapNullValue);
    }




}
