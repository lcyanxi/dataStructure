package com.douguo.ndc.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.douguo.ndc.model.CookTag;
import com.douguo.ndc.model.FoodTag;
import com.douguo.ndc.model.NutrimentTag;
import com.douguo.ndc.model.TagInfo;
import com.douguo.ndc.service.ChronTagService;
import com.douguo.ndc.util.ConstantStr;
import com.douguo.ndc.util.DateUtil;
import com.douguo.ndc.util.QueryTableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/23
 */
@Controller @RequestMapping(value = "/chron") public class ChronTagController {

    @Autowired private ChronTagService chronTagService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET) public String toChronTagePage() {
        return "pages/cook_tag";
    }

    @RequestMapping(value = "/toTagInfoPage", method = RequestMethod.GET) public String toTagInfoPage() {
        return "pages/tag_info";
    }

    @RequestMapping(value = "/toFoodTagPage", method = RequestMethod.GET) public String toFoodTagPage() {
        return "pages/food_tag";
    }

    @RequestMapping(value = "/toNutrimentTagPage", method = RequestMethod.GET) public String toNutrimentTagPage() {
        return "pages/nutriment_tag";
    }

    @RequestMapping(value = "/queryData", method = RequestMethod.GET) @ResponseBody
    public String queryChronTagData(@RequestParam String aoData) {

        Map map = QueryTableUtil.getQueryTableParms(aoData);
        String sSearch = (String)map.get(ConstantStr.SSEARCH);

        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj = chronTagService.getSsearchData(sSearch);
        } else {
            getObj = chronTagService.getChronTagData((Integer)map.get(ConstantStr.IDISPLAYSTART),
                (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }
        //解决fastJson key值为null数据丢失问题
        String str = JSONObject.toJSONString(getObj, SerializerFeature.WriteMapNullValue);
        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        return str;

    }

    @RequestMapping(value = "/queryTagInfoData", method = RequestMethod.GET) @ResponseBody
    public String queryTagInfoData(@RequestParam String aoData) {

        Map map = QueryTableUtil.getQueryTableParms(aoData);

        String sSearch = (String)map.get(ConstantStr.SSEARCH);

        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj = chronTagService.getTagInfoSsearchData(sSearch);
        } else {
            getObj = chronTagService.getTagInfoData((Integer)map.get(ConstantStr.IDISPLAYSTART),
                (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }
        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        return getObj.toString();

    }

    @RequestMapping(value = "/queryFoodTagData", method = RequestMethod.GET) @ResponseBody
    public String queryFoodTagData(@RequestParam String aoData) {

        Map map = QueryTableUtil.getQueryTableParms(aoData);
        String sSearch = (String)map.get(ConstantStr.SSEARCH);

        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj = chronTagService.getFoodTagSsearchData(sSearch);
        } else {
            getObj = chronTagService.getFoodTagData((Integer)map.get(ConstantStr.IDISPLAYSTART),
                (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }
        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        return getObj.toString();

    }

    @RequestMapping(value = "/queryNutrimentTagData", method = RequestMethod.GET) @ResponseBody
    public String queryNutrimentTagData(@RequestParam String aoData) {

        Map map = QueryTableUtil.getQueryTableParms(aoData);

        String sSearch = (String)map.get(ConstantStr.SSEARCH);

        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj = chronTagService.getNutrimentTagSsearchData(sSearch);
        } else {
            getObj = chronTagService.getNutrimentTagData((Integer)map.get(ConstantStr.IDISPLAYSTART),
                (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }
        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        return getObj.toString();

    }

    @RequestMapping(value = "/insertFoodTag", method = RequestMethod.POST) @ResponseBody
    public Map insertFoodTag(FoodTag foodTag) {
        Map<String, Object> map = new HashMap<>(16);

        if (chronTagService.insertFoodTag(foodTag)) {
            map.put(ConstantStr.MESSAGE, "添加成功");
        } else {
            map.put(ConstantStr.MESSAGE, "添加失败，食材名称或标签已经存在");
        }
        map.put(ConstantStr.SATATUSCODE, 200);
        return map;

    }

    @RequestMapping(value = "/updateChronTag", method = RequestMethod.POST) @ResponseBody
    public Map updateChronTag(CookTag cookTag) {
        Map<String, Object> map = new HashMap<>(16);

        cookTag.setUpdateTime(DateUtil.getDate());

        if (chronTagService.updateChronTag(cookTag)) {
            map.put(ConstantStr.MESSAGE, "更新成功");
        } else {
            map.put(ConstantStr.MESSAGE, "更新失败");
        }

        map.put(ConstantStr.SATATUSCODE, 200);
        return map;

    }

    @RequestMapping(value = "/updateTagInfo", method = RequestMethod.POST) @ResponseBody
    public Map updateTagInfo(TagInfo tagInfo) {
        Map<String, Object> map = new HashMap<>(16);

        if (chronTagService.updateTagInfo(tagInfo)) {
            map.put(ConstantStr.MESSAGE, "更新成功");
        } else {
            map.put(ConstantStr.MESSAGE, "更新失败");
        }

        map.put(ConstantStr.SATATUSCODE, 200);
        return map;

    }

    @RequestMapping(value = "/updateFoodTag", method = RequestMethod.POST) @ResponseBody
    public Map updateTagInfo(FoodTag foodTag) {
        Map<String, Object> map = new HashMap<>(16);

        if (chronTagService.updateFoodTag(foodTag)) {
            map.put(ConstantStr.MESSAGE, "更新成功");
        } else {
            map.put(ConstantStr.MESSAGE, "更新失败");
        }

        map.put(ConstantStr.SATATUSCODE, 200);
        return map;

    }

    @RequestMapping(value = "/updateNutrimentTag", method = RequestMethod.POST) @ResponseBody
    public Map updateNutrimentTag(NutrimentTag nutrimentTag) {
        Map<String, Object> map = new HashMap<>(16);

        if (chronTagService.updateNutrimentTag(nutrimentTag)) {
            map.put(ConstantStr.MESSAGE, "更新成功");
        } else {
            map.put(ConstantStr.MESSAGE, "更新失败");
        }

        map.put(ConstantStr.SATATUSCODE, 200);
        return map;

    }
}
