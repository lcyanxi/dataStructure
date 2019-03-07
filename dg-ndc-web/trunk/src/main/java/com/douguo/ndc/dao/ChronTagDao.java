package com.douguo.ndc.dao;

import com.douguo.ndc.model.CookTag;
import com.douguo.ndc.model.FoodTag;
import com.douguo.ndc.model.NutrimentTag;
import com.douguo.ndc.model.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/24
 */
@Repository public class ChronTagDao {

    @Autowired protected JdbcTemplate secondaryJdbcTemplate;

    public List<CookTag> queryAllLimitPage(int iDisplayStart, int iDisplayLength) {
        String queryAllSQLLimitPage = "SELECT * FROM dd_cook_tag order by create_time desc,id DESC limit ?,?";
        Object[] params = new Object[] {iDisplayStart, iDisplayLength};
        List<CookTag> list = secondaryJdbcTemplate.query(queryAllSQLLimitPage, params, new ChronTagRowMapper());
        return list;
    }

    public boolean updateChronTag(CookTag cookTag) {
        String updateSQL = "update dd_cook_tag set status=?,level=?,update_time=? where id=?";

        int n = secondaryJdbcTemplate
            .update(updateSQL, cookTag.getStatus(), cookTag.getLevel(), cookTag.getUpdateTime(), cookTag.getId());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<TagInfo> queryAllTagInfoLimit(int iDisplayStart, int iDisplayLength) {
        String queryAllSQLLimitPage = "SELECT * FROM dd_tag_info order by id DESC limit ?,?";
        Object[] params = new Object[] {iDisplayStart, iDisplayLength};
        List<TagInfo> list = secondaryJdbcTemplate.query(queryAllSQLLimitPage, params, new TageInfoRowMapper());
        return list;
    }

    public boolean updateTagInfo(TagInfo tagInfo) {
        String updateSQL = "update dd_tag_info set tag=? ,type=?,sort=?,level=?,tag_desc=? where id=?";
        int n = secondaryJdbcTemplate
            .update(updateSQL, tagInfo.getTag(), tagInfo.getType(), tagInfo.getSort(), tagInfo.getLevel(),
                tagInfo.getDesc(), tagInfo.getId());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<FoodTag> queryAllFoodTagLimit(int iDisplayStart, int iDisplayLength) {
        String queryAllSQLLimitPage = "SELECT * FROM dd_food_tag order by id DESC limit ?,?";
        Object[] params = new Object[] {iDisplayStart, iDisplayLength};
        List<FoodTag> list = secondaryJdbcTemplate.query(queryAllSQLLimitPage, params, new FoodTagRowMapper());
        return list;
    }

    public List<FoodTag> queryFoodTagByFoodNameAndTag(String foodName,String tag) {
        String queryAllSQL = "SELECT * FROM dd_food_tag where food_name=? and tag=?";
        Object[] params = new Object[] {foodName,tag};
        List<FoodTag> list = secondaryJdbcTemplate.query(queryAllSQL, params, new FoodTagRowMapper());
        return list;
    }

    public boolean updateFoodTag(FoodTag foodTag) {
        String updateSQL = "update dd_food_tag set food_id=? ,food_name=?,tag=?,confidence_interval=? where id=?";
        int n = secondaryJdbcTemplate.update(updateSQL, foodTag.getFoodId(), foodTag.getFoodName(), foodTag.getTag(),
            foodTag.getConfidenceInterval(), foodTag.getId());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean insertFoodTag(FoodTag foodTag) {
        String insertSQL = "insert into  dd_food_tag (food_id,food_name,type,tag,confidence_interval) VALUES (?,?,?,?,?)";
        int n = secondaryJdbcTemplate.update(insertSQL, foodTag.getFoodId(), foodTag.getFoodName(),foodTag.getType(), foodTag.getTag(),
            foodTag.getConfidenceInterval());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<NutrimentTag> queryAllNutrimentTagLimit(int iDisplayStart, int iDisplayLength) {
        String queryAllSQLLimitPage = "SELECT * FROM dd_nutriment_tag order by id DESC limit ?,?";
        Object[] params = new Object[] {iDisplayStart, iDisplayLength};
        List<NutrimentTag> list = secondaryJdbcTemplate.query(queryAllSQLLimitPage, params, new NutrimentTagRowMapper());
        return list;
    }

    public boolean updateNutrimentTag(NutrimentTag nutrimentTag) {
        String updateSQL =
            "update dd_nutriment_tag set nutriment_id=? ,nutriment_name=?,tag=?,confidence_interval=? where id=?";
        int n = secondaryJdbcTemplate
            .update(updateSQL, nutrimentTag.getNutrimentId(), nutrimentTag.getNutrimentName(), nutrimentTag.getTag(),
                nutrimentTag.getConfidenceInterval(), nutrimentTag.getId());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<CookTag> querySsearch(String sSearch) {
        String sSearchSQL = "select * from dd_cook_tag where cook_id LIKE '" + sSearch + "' or tag like '%" + sSearch
            + "%' order by id DESC ";
        List<CookTag> list = secondaryJdbcTemplate.query(sSearchSQL, new ChronTagRowMapper());
        return list;
    }

    public List<TagInfo> queryTagInfoSsearch(String sSearch) {
        String sSearchSQL = "select * from dd_tag_info where tag LIKE '%" + sSearch + "%' or tag_desc like '%" + sSearch
            + "%' order by id DESC ";
        List<TagInfo> list = secondaryJdbcTemplate.query(sSearchSQL, new TageInfoRowMapper());
        return list;
    }

    public List<FoodTag> queryFoodTagSsearch(String sSearch) {
        String sSearchSQL =
            "select * from dd_food_tag where food_name LIKE '%" + sSearch + "%' or tag like '%" + sSearch
                + "%' order by id DESC ";
        List<FoodTag> list = secondaryJdbcTemplate.query(sSearchSQL, new FoodTagRowMapper());
        return list;
    }

    public List<NutrimentTag> queryNutrimentTagSsearch(String sSearch) {
        String sSearchSQL =
            "select * from dd_nutriment_tag where nutriment_name LIKE '%" + sSearch + "%' or tag like '%" + sSearch
                + "%' order by id DESC ";
        List<NutrimentTag> list = secondaryJdbcTemplate.query(sSearchSQL, new NutrimentTagRowMapper());
        return list;
    }

    private class ChronTagRowMapper implements RowMapper<CookTag> {

        @Override public CookTag mapRow(ResultSet rs, int rowNum) throws SQLException {
            CookTag cookTag = new CookTag();
            cookTag.setId(rs.getInt("id"));
            cookTag.setCookId(rs.getInt("cook_id"));
            cookTag.setCookName(rs.getString("cook_name"));
            cookTag.setTagId(rs.getInt("tag_id"));
            cookTag.setTag(rs.getString("tag"));
            cookTag.setLevel(rs.getInt("level"));
            cookTag.setStatus(rs.getInt("status"));
            cookTag.setCreateTime(rs.getString("create_time"));
            cookTag.setUpdateTime(rs.getString("update_time"));
            return cookTag;
        }
    }

    private class TageInfoRowMapper implements RowMapper<TagInfo> {

        @Override public TagInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            TagInfo tagInfo = new TagInfo();
            tagInfo.setId(rs.getInt("id"));
            tagInfo.setTag(rs.getString("tag"));
            tagInfo.setType(rs.getString("type"));
            tagInfo.setSort(rs.getInt("sort"));
            tagInfo.setLevel(rs.getInt("level"));
            tagInfo.setDesc(rs.getString("tag_desc"));
            return tagInfo;
        }
    }

    private class FoodTagRowMapper implements RowMapper<FoodTag> {

        @Override public FoodTag mapRow(ResultSet rs, int rowNum) throws SQLException {
            FoodTag foodTag = new FoodTag();
            foodTag.setId(rs.getInt("id"));
            foodTag.setType(rs.getInt("type"));
            foodTag.setFoodId(rs.getInt("food_id"));
            foodTag.setFoodName(rs.getString("food_name"));
            foodTag.setTag(rs.getString("tag"));
            foodTag.setConfidenceInterval(rs.getFloat("confidence_interval"));
            return foodTag;
        }
    }

    private class NutrimentTagRowMapper implements RowMapper<NutrimentTag> {

        @Override public NutrimentTag mapRow(ResultSet rs, int rowNum) throws SQLException {

            NutrimentTag nutrimentTag = new NutrimentTag();
            nutrimentTag.setId(rs.getInt("id"));
            nutrimentTag.setConfidenceInterval(rs.getFloat("confidence_interval"));
            nutrimentTag.setNutrimentName(rs.getString("nutriment_name"));
            nutrimentTag.setTag(rs.getString("tag"));
            nutrimentTag.setNutrimentId(rs.getString("nutriment_id"));
            return nutrimentTag;
        }
    }

}
