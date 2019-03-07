package com.douguo.ndc.service;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.dao.CommonDao;
import com.douguo.ndc.dao.TencentStatDao;
import com.douguo.ndc.model.TencentStat;
import com.douguo.ndc.util.QueryTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2019/1/15
 */
@Service
public class TencentTagApiService {

    @Autowired
    private TencentStatDao tencentStatDao;

    @Autowired
    private CommonDao commonDao;

    public JSONObject getData(int iDisplayStart, int iDisplayLength) {
        List<TencentStat> list = tencentStatDao.queryAllLimitPage(iDisplayStart, iDisplayLength);
        String queryAllSQLCount = "SELECT count(*) FROM dd_tencent_tag_stat";
        int totalSize = commonDao.queryAllCount(queryAllSQLCount);
        return QueryTableUtil.converToJSONObject(list,totalSize);
    }

    public boolean insert(TencentStat tencentStat){
        return tencentStatDao.insertTag(tencentStat);
    }

    public List queryForName(String name,String tagCode){
        return tencentStatDao.queryForName(name,tagCode);
    }

    public JSONObject getSsearchData(String sSearch) {
        return QueryTableUtil.converToJSONObject(tencentStatDao.querySsearch(sSearch));
    }

    public boolean delete(String id){
        return tencentStatDao.deleteTag(id);
    }

}
