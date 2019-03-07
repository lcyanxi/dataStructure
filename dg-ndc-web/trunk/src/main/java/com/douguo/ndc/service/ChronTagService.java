package com.douguo.ndc.service;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.dao.ChronTagDao;
import com.douguo.ndc.dao.CommonDao;
import com.douguo.ndc.model.CookTag;
import com.douguo.ndc.model.FoodTag;
import com.douguo.ndc.model.NutrimentTag;
import com.douguo.ndc.model.TagInfo;
import com.douguo.ndc.util.ConstantStr;
import com.douguo.ndc.util.QueryTableUtil;
import com.douguo.ndc.util.RedisConstantStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/24
 */
@Service public class ChronTagService {

    @Autowired private ChronTagDao chronTagDao;
    @Autowired private CommonDao commonDao;

    //@Cacheable(key = "'cook_tag_'+#iDisplayStart+'_'+#iDisplayLength",value = RedisConstantStr.GET_COOK_TAG)
    public JSONObject getChronTagData(int iDisplayStart, int iDisplayLength) {
        List<CookTag> list = chronTagDao.queryAllLimitPage(iDisplayStart, iDisplayLength);
        for (CookTag cookTag:list){
            cookTag.setStatusDetail(converCookTagStatus(cookTag.getStatus()));
        }
        String queryAllSQLCount = "SELECT count(*) FROM dd_cook_tag";
        int totalSize = commonDao.queryAllCount(queryAllSQLCount);
        return QueryTableUtil.converToJSONObject(list,totalSize);
    }


    public JSONObject getTagInfoData(int iDisplayStart, int iDisplayLength) {
        List<TagInfo> list = chronTagDao.queryAllTagInfoLimit(iDisplayStart, iDisplayLength);
        String queryAllSQLCount = "SELECT count(*) FROM dd_tag_info";
        int totalSize = commonDao.queryAllCount(queryAllSQLCount);
        return QueryTableUtil.converToJSONObject(list,totalSize);
    }

    public JSONObject getFoodTagData(int iDisplayStart, int iDisplayLength) {
        List<FoodTag> list = chronTagDao.queryAllFoodTagLimit(iDisplayStart, iDisplayLength);
        String queryAllSQLCount = "SELECT count(*) FROM dd_food_tag";
        int totalSize = commonDao.queryAllCount(queryAllSQLCount);
        return QueryTableUtil.converToJSONObject(list,totalSize);
    }

    public JSONObject getNutrimentTagData(int iDisplayStart, int iDisplayLength) {
        List<NutrimentTag> list = chronTagDao.queryAllNutrimentTagLimit(iDisplayStart, iDisplayLength);
        String queryAllSQLCount = "SELECT count(*) FROM dd_nutriment_tag";
        int totalSize = commonDao.queryAllCount(queryAllSQLCount);
        return QueryTableUtil.converToJSONObject(list,totalSize);
    }

    /**
     * 添加操作
     * @param foodTag
     * @return
     */

    public boolean insertFoodTag(FoodTag foodTag){
        List list=chronTagDao.queryFoodTagByFoodNameAndTag(foodTag.getFoodName(),foodTag.getTag());

        if (list.isEmpty()){
            return chronTagDao.insertFoodTag(foodTag);
        }
        return false;
    }

    /**
     * 更新操作
     * @param cookTag
     * @return
     */
    @CacheEvict(value = RedisConstantStr.GET_COOK_TAG)
    public boolean updateChronTag(CookTag cookTag) {
        return chronTagDao.updateChronTag(cookTag);
    }

    public boolean updateTagInfo(TagInfo tagInfo) {
        return chronTagDao.updateTagInfo(tagInfo);
    }

    public boolean updateFoodTag(FoodTag foodTag) {
        return chronTagDao.updateFoodTag(foodTag);
    }

    public boolean updateNutrimentTag(NutrimentTag nutrimentTag) {
        return chronTagDao.updateNutrimentTag(nutrimentTag);
    }


    /**
     *  搜索框查询数据
     * @param sSearch
     * @return
     */
    public JSONObject getSsearchData(String sSearch) {
        return QueryTableUtil.converToJSONObject(chronTagDao.querySsearch(sSearch));
    }


    public JSONObject getTagInfoSsearchData(String sSearch) {
        return QueryTableUtil.converToJSONObject(chronTagDao.queryTagInfoSsearch(sSearch));

    }

    public JSONObject getFoodTagSsearchData(String sSearch) {
        return QueryTableUtil.converToJSONObject(chronTagDao.queryFoodTagSsearch(sSearch));
    }

    public JSONObject getNutrimentTagSsearchData(String sSearch) {
        return QueryTableUtil.converToJSONObject(chronTagDao.queryNutrimentTagSsearch(sSearch));
    }



    private String  converCookTagStatus(int status){

        if (status==-1){
             return  "删除";
        }else if (status==0){
            return "后续可以删除";
        }else if (status==1){
            return "人工审核";
        }else if (status==2){
            return "专家审核";
        }else {
            return "";
        }
    }



}
