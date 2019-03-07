package com.douguo.ndc.caipusearch.esdemo.service;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.caipusearch.esdemo.esutil.SearchWordCrudApp;
import com.douguo.ndc.caipusearch.routadjust.service.SearchWordAdjusetService;
import com.douguo.ndc.caipusearch.routchoose.service.RoutChooseService;
import com.douguo.ndc.dao.CommonDao;
import com.douguo.ndc.util.JsonUtil;
import com.douguo.ndc.util.QueryTableUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/10
 */
@Service
public class EsSearchService {

    private static final String KEYWORD="keyword";

    @Resource(name = "caipuEsClient") private TransportClient client;
    @Autowired private RoutChooseService routChooseService;
    @Autowired private SearchWordAdjusetService searchWordAdjusetService;
    @Autowired private SearchWordCrudApp searchWordCrudApp;
    @Autowired private CommonDao commonDao;

    /**
     *  1.判断输入查询类型  直接找相应索引
     *  2.如果没有输入类型  先去自定义维护表查找相应的类型
     *  3.如果自定义维护表为空  则去食材、标签表查找执行类型
     */
    public String querySearchWord(String searchWord,String searchType){
        StringBuffer accessRout=new StringBuffer();
        JSONObject resultJson;
        //先判断q类型是否为空
        if (StringUtils.isBlank(searchType)){
            accessRout.append("q判断为空");
            String excludeKey="";
            //先去食材标签调整表里获取查询方式
            JSONObject jsonObject=searchWordAdjusetService.querySearchWordAdjust(searchWord);
            System.out.println("categories+ingredients:"+jsonObject);

            if (jsonObject.isEmpty()){
                accessRout.append("->自定义维护表为空");
                //再去食材标签表里获取查询的方式
                searchType=routChooseService.querySearchWord(searchWord);
                accessRout.append("->食材/标签表");
            }else {
                accessRout.append("->自定义维护表");
                searchType=jsonObject.getString("type");
                excludeKey=jsonObject.getString("exclude_key");
            }
            accessRout.append("->查询方式:"+searchType);
            if (searchType.equals(KEYWORD)){
                // 走关键词查询,去除要排除的词
               // resultJson= searchWordCrudApp.executeSearch(client, searchWord,excludeKey,accessRout);
                resultJson= searchWordCrudApp.executeSearchNew(client, searchWord,accessRout);
            }else {
                // 走食材或者标签查询
                resultJson= searchWordCrudApp.executeSearch(client,searchType,searchWord);
                accessRout.append("->搜索词结果数量"+resultJson.get("total"));
            }
        }else {
            accessRout.append("走q判断");
            accessRout.append("->查询方式:"+searchType);
            if (searchType.equals(KEYWORD)){
                // 走关键词查询
                resultJson= searchWordCrudApp.executeSearchNew(client,searchWord,accessRout);
            }else {
                // 走食材或者标签查询
                resultJson= searchWordCrudApp.executeSearch(client,searchType,searchWord);
                accessRout.append("->搜索词结果数量"+resultJson.get("total"));
            }
        }
        accessRout.append("->END");
        resultJson.put("searchType", searchType);
        resultJson.put("searchWord",searchWord);
        resultJson.put("rout",accessRout);

        System.out.println(searchWord+":"+showCookIds(resultJson.toJSONString()));
        return resultJson.toJSONString();

    }

    public JSONObject querySearchWordAdjust(int iDisplayStart, int iDisplayLength){
        List list=searchWordAdjusetService.querySearchWordAdjustLimitPage(iDisplayStart,iDisplayLength);
        String queryAllSQLCount = "SELECT count(*) FROM dd_searchword_adjust";
        int totalSize = commonDao.queryAllCount(queryAllSQLCount);
        return QueryTableUtil.converToJSONObject(list,totalSize);
    }

    /**
     * 食材标签页面搜索框查询数据
     * @param searchWord
     * @return
     */
    public JSONObject querySearchWordAdjustSsearch(String searchWord){
        return QueryTableUtil.converToJSONObject(searchWordAdjusetService.querySearchWordAdjustSsearch(searchWord));
    }

    public static void main(String[] args) throws Exception {
        // 先构建client
        Settings settings = Settings.builder().put("cluster.name", "dg_es1_cluster").build();

        TransportClient client = TransportClient.builder().settings(settings).build()
            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.235"), 9300));


        		//SearchWordCrudApp.createEmployee(client);
        		//SearchWordCrudApp.getEmployee(client);
        		//SearchWordCrudApp.prepareData(client);
        		//SearchWordCrudApp.executeSearchbak(client,"keyword","鱼香肉丝","");
        //		updateEmployee(client);
        //		deleteEmployee(client);
        //SearchWordCrudApp.getOneTagForAllData(client,"all","","all",50,"2000-01-01","2018-12-12","0");
        System.out.println("-----------------------------");

        client.close();
    }

    private String  showCookIds(String jsonString){
        JSONObject  json= JsonUtil.parseStrToJsonObj(jsonString);
        List<Map<String,Object>> list=(List)json.get("data");
        StringBuffer ids=new StringBuffer();
        StringBuffer buffer=new StringBuffer();

        int index=0;
        for (Map<String,Object> map:list){
            buffer.append(map.get("cook_name")+",");
            ids.append(map.get("cook_id")+",");
            index++;
            if (index>=20){
                break;
            }
        }
        System.out.println("index:"+index);
        if (org.apache.commons.lang.StringUtils.isNotBlank(ids.toString()))
            ids.deleteCharAt(ids.length()-1);

        return ids.toString();
    }
}
