package com.douguo.ndc.service;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.dao.CommonDao;
import com.douguo.ndc.dao.EventDao;
import com.douguo.ndc.util.ConstantStr;
import com.douguo.ndc.util.QueryTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/12
 */
@Service public class EventService {

    @Autowired private EventDao eventDao;

    @Autowired private CommonDao commonDao;

    public JSONObject queryData(String dimensionType, String appId, String date, String eventCode, String paramKey,
        int iDisplayStart, int iDisplayLength) {

        int total = commonDao.queryParamCount(dimensionType, appId, date, eventCode, paramKey);

        List list =
            eventDao.queryAllLimitPage(appId, dimensionType, eventCode, paramKey, date, iDisplayStart, iDisplayLength);

        return QueryTableUtil.converToJSONObject(list, total);
    }

    public JSONObject querySearchData(String dimensionType, String appId, String date, String eventCode, String paramKey,String search) {
        List list =
            eventDao.querySearch(appId, dimensionType, eventCode, paramKey, date,search);

        return QueryTableUtil.converToJSONObject(list);
    }
}
