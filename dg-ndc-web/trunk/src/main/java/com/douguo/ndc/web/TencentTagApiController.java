package com.douguo.ndc.web;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.model.TencentStat;
import com.douguo.ndc.service.TencentTagApiService;
import com.douguo.ndc.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2019/1/15
 */
@Controller
@RequestMapping("/tencent")
public class TencentTagApiController {

    @Autowired
    private TencentTagApiService tencentTagApiService;



    @GetMapping(value = "/toPages")
    public String toPage(){
        return "pages/tencent_tagApi";
    }

    @GetMapping(value = "/add_tag")
    @ResponseBody
    public Map addTag(@RequestParam(value = "parentTagId",required = false,defaultValue = "0") String parentTagId,String name,
                      String tagCode){
        Map<String, Object> map = new HashMap<>(16);

        if (!tencentTagApiService.queryForName(name,tagCode).isEmpty()){
            map.put(ConstantStr.MESSAGE, "添加失败,标签已经存在");
            return map;
        }
        Long starTime=System.currentTimeMillis()/1000;
        String url="https://api.e.qq.com/v1.1/custom_tags/add?access_token=83ea5be46336546e0ef828a6c4493d11&timestamp="+starTime+"&nonce="+starTime;
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("account_id","9365385");
        jsonObject.put("name",name);
        jsonObject.put("tag_code", tagCode);
        jsonObject.put("description",name);
        jsonObject.put("parent_tag_id",parentTagId);
        String result=HttpClientUtil.postJson(url,jsonObject.toJSONString());
        JSONObject object=JsonUtil.parseStrToJsonObj(result);

        String code=object.getString("code");
        if (!code.equals("0")){
            map.put(ConstantStr.MESSAGE, "请求接口服务失败");
            return map;
        }

        String tag_id=JsonUtil.parseStrToJsonObj(object.getString("data")).getString("tag_id");

        TencentStat tencentStat=new TencentStat();
        tencentStat.setTagCode(tagCode);
        tencentStat.setTagId(tag_id);
        tencentStat.setCreateTime(DateUtil.getDate());
        tencentStat.setParentTagId(parentTagId);
        tencentStat.setName(name);

        if ( tencentTagApiService.insert(tencentStat)) {
            map.put(ConstantStr.MESSAGE, "添加成功");
        } else {
            map.put(ConstantStr.MESSAGE, "添加失败");
        }
        map.put(ConstantStr.SATATUSCODE, 200);
        return map;

    }



    @RequestMapping(value = "/queryData", method = RequestMethod.GET) @ResponseBody
    public String queryNutrimentTagData(@RequestParam String aoData) {

        Map map = QueryTableUtil.getQueryTableParms(aoData);
        String sSearch = (String)map.get(ConstantStr.SSEARCH);
        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj = tencentTagApiService.getSsearchData(sSearch);
        } else {
            getObj = tencentTagApiService.getData((Integer)map.get(ConstantStr.IDISPLAYSTART),
                (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }
        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        return getObj.toString();

    }



    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadExcelData(@RequestParam("file") MultipartFile file,@RequestParam("tagId") String tagId) {
        System.out.println("tagId"+tagId);
        Long starTime=System.currentTimeMillis()/1000;
        String url="https://api.e.qq.com/v1.1/custom_tag_files/add?access_token=83ea5be46336546e0ef828a6c4493d11&timestamp="+starTime+"&nonce="+starTime;
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("account_id","9365385");
//        jsonObject.put("user_id_type","IDFA");
//        jsonObject.put("tag_id", tagId);
//        jsonObject.put("file",file);
        //System.out.println("file"+file);
        //System.out.println("result:"+result);
        //JSONObject object=JsonUtil.parseStrToJsonObj(result);
        return null;
    }


    @RequestMapping(value = "/delete_tag", method = RequestMethod.GET) @ResponseBody
    public Map deleteTag(String id) {
        Map map = new HashMap(16);
        tencentTagApiService.delete(id);
        map.put(ConstantStr.MESSAGE, "删除成功");
        return map;
    }

}
