package com.douguo.ndc.datashow.web;

import com.douguo.ndc.datashow.service.DataShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/27
 */
@Controller
@RequestMapping("/datashow")
public class DataShowController {
    @Autowired
    private DataShowService dataShowService;


    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public String queryList() {
        return "pages/datashow/bar_show";
    }

    @RequestMapping(value = "/worldData", method = RequestMethod.GET)
    @ResponseBody
    public Map queryWorldData() {
        long begindata= System.currentTimeMillis();

        Map map = new HashMap(16);
        map.put("barData", dataShowService.queryAllData());
        map.put("caipuData", dataShowService.queryBehavior());
        map.put("provCityData", dataShowService.queryCityData());
        long endData=System.currentTimeMillis();
        System.out.println("worldData获取数据时间:"+(endData-begindata));
        return map;
    }

    @RequestMapping(value = "/toRoastShow", method = RequestMethod.GET)
    public String toDataShowPage() {
        return "pages/datashow/douguo_roast_show";
    }


    @RequestMapping(value = "/toDouguoPersonShow", method = RequestMethod.GET)
    public String toDouguoPersonShowPage() {
        return "pages/datashow/douguo_person_show";
    }


    @RequestMapping(value = "/numData", method = RequestMethod.GET)
    @ResponseBody
    public Map queryNumData() {
        Map<String,Object> map = new HashMap<>(16);
        int num = dataShowService.getPersonNum();
        map.put("numData", num);
        return map;
    }


    @RequestMapping(value = "/queryAllData", method = RequestMethod.GET)
    @ResponseBody
    public Map queryAllData() {

        long begindata= System.currentTimeMillis();
        Map map=dataShowService.queryDgData();
        long endData=System.currentTimeMillis();
        System.out.println("queryAllData 获取数据时间:"+(endData-begindata));
        return map;
    }

}