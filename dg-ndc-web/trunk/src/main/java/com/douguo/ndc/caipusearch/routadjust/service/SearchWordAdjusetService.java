package com.douguo.ndc.caipusearch.routadjust.service;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.caipusearch.routadjust.dao.SearchWordAdjustDao;
import com.douguo.ndc.caipusearch.routadjust.model.SearchWordAdjust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/12
 */
@Service
public class SearchWordAdjusetService {
    @Autowired private SearchWordAdjustDao searchWordAdjustDao;


    public JSONObject querySearchWordAdjust(String searchword){

        JSONObject jsonObject=new JSONObject();

        List<Map<String,Object>> list=searchWordAdjustDao.querySearchWordAdjust();
        for (Map<String,Object> map:list){
            String key=(String)map.get("key_word");
            if (key.equals(searchword)){
                jsonObject.put("type",map.get("type"));
                jsonObject.put("exclude_key",map.get("exclude_key"));
                break;
            }
        }

        return jsonObject;
    }

    public List<SearchWordAdjust> querySearchWordAdjustLimitPage(int iDisplayStart, int iDisplayLength){
        return searchWordAdjustDao.querySearchWordAdjustLimitPage(iDisplayStart,iDisplayLength);
    }

    public List<SearchWordAdjust> querySearchWordAdjustSsearch(String sSearch){
        return searchWordAdjustDao.querySearchWordAdjustSsearch(sSearch);
    }

    public boolean addAdjustWord(SearchWordAdjust adjust){
        String excludeKey=adjust.getExclude_key();
        excludeKey.replaceAll("，",",");
        adjust.setExclude_key(excludeKey);
        return searchWordAdjustDao.insertAdjustWord(adjust);
    }
    public boolean updateAdjustWord(SearchWordAdjust adjust){
        String excludeKey=adjust.getExclude_key();
        excludeKey.replaceAll("，",",");
        adjust.setExclude_key(excludeKey);
        return searchWordAdjustDao.updateAdjustWord(adjust);
    }
    public boolean deleteAdjustWord(String id){
        return searchWordAdjustDao.deleteAdjustWord(id);
    }

}
