package com.douguo.ndc.caipusearch.esdemo.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.douguo.ndc.caipusearch.esdemo.service.EsSearchService;
import com.douguo.ndc.caipusearch.routadjust.model.SearchWordAdjust;
import com.douguo.ndc.caipusearch.routadjust.service.SearchWordAdjusetService;
import com.douguo.ndc.caipusearch.util.SearchTypeUtil;
import com.douguo.ndc.util.ConstantStr;
import com.douguo.ndc.util.HttpClientUtil;
import com.douguo.ndc.util.QueryTableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/9
 */
@Controller @RequestMapping("/es") public class EsSearchController {
    @Value("${caipu.search.http.client.url}") private String caipu_search_http_client_url;
    @Autowired private EsSearchService esSearchService;
    @Autowired private SearchWordAdjusetService searchWordAdjusetService;

    @GetMapping(value = "/toPage/{searchWord}") public ModelAndView toPages(@PathVariable(value = "searchWord",required =false) String searchWord,
        @RequestParam(value = "q", required = false) String q,@RequestParam(value = "type",required = false,defaultValue = "") String type) {
       ModelAndView modelAndView =new ModelAndView("pages/search/es_search");


       String url;
       if (type.equals("1")){
           if (StringUtils.isBlank(q)) url=caipu_search_http_client_url+"/es/searchWord/"+URLEncoder.encode(searchWord);
           else url=caipu_search_http_client_url+"/es/searchWord/"+URLEncoder.encode(searchWord)+"?q="+q;
       }else {
           if (StringUtils.isBlank(q)) url=caipu_search_http_client_url+"/es/q/"+URLEncoder.encode(searchWord);
           else  url=caipu_search_http_client_url+"/es/q/"+URLEncoder.encode(searchWord)+"?se="+q;
       }

        String result=HttpClientUtil.get(url);
        System.out.println("result"+result);
        modelAndView.addObject("data",result==null?"":result);
        //modelAndView.addObject("data",esSearchService.querySearchWord(searchWord, SearchTypeUtil.getSearchType(q)));
        return modelAndView;
    }

    @GetMapping(value = "/toSearchAdjustPage") public String toSearchAdjustPage() {
        return "pages/search/search_adjust";
    }

    @GetMapping(value = "/searchWord/{searchWord}") @ResponseBody
    public String searchWord(@PathVariable(value = "searchWord") String searchWord, @RequestParam(value = "q", required = false) String q) {
        return esSearchService.querySearchWord(searchWord,"");
    }

    @GetMapping(value = "/searchAdjustData") @ResponseBody public String searchAdjustData(@RequestParam String aoData) {
        Map map = QueryTableUtil.getQueryTableParms(aoData);
        String sSearch = (String)map.get(ConstantStr.SSEARCH);

        JSONObject getObj;

        if (StringUtils.isNotBlank(sSearch)) {
            getObj = esSearchService.querySearchWordAdjustSsearch(sSearch);
        } else {
            getObj = esSearchService.querySearchWordAdjust((Integer)map.get(ConstantStr.IDISPLAYSTART),
                (Integer)map.get(ConstantStr.IDISPLAYLENGTH));
        }
        getObj.put(ConstantStr.SECHO, map.get(ConstantStr.SECHO));
        //解决fastJson key值为null数据丢失问题
        return JSONObject.toJSONString(getObj, SerializerFeature.WriteMapNullValue);
    }

    @PostMapping(value = "/addAdjustWord") @ResponseBody public Map addAdjustWord(SearchWordAdjust adjust) {
        System.out.println(adjust);
        Map<String, Object> map = new HashMap<>(16);

        if (searchWordAdjusetService.addAdjustWord(adjust)) {
            map.put(ConstantStr.MESSAGE, "添加成功");
        } else {
            map.put(ConstantStr.MESSAGE, "添加失败");
        }

        map.put(ConstantStr.SATATUSCODE, 200);
        return map;
    }

    @PostMapping(value = "/updateAdjustWord") @ResponseBody public Map updateAdjustWord(SearchWordAdjust adjust) {
        Map<String, Object> map = new HashMap<>(16);

        if (searchWordAdjusetService.updateAdjustWord(adjust)) {
            map.put(ConstantStr.MESSAGE, "更新成功");
        } else {
            map.put(ConstantStr.MESSAGE, "更新失败");
        }

        map.put(ConstantStr.SATATUSCODE, 200);
        return map;
    }

    @GetMapping(value = "/deleteAdjustWord") @ResponseBody public Map deleteAdjustWord(String id) {

        Map map = new HashMap(16);
        searchWordAdjusetService.deleteAdjustWord(id);
        map.put(ConstantStr.MESSAGE, "删除成功");
        return map;

    }

}
