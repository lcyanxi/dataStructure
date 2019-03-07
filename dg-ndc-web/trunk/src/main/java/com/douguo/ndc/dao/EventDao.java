package com.douguo.ndc.dao;

import com.douguo.ndc.model.Event;
import com.douguo.ndc.util.ConstantStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * Date: 2018/11/12
 */
@Repository public class EventDao {

    @Autowired @Qualifier("secondaryJdbcTemplate") private JdbcTemplate douguodataJdbcTemplate;

    public List<Event> queryAllLimitPage(String appId, String dimensionType, String version, String date,
        int iDisplayStart, int iDisplayLength) {
        String queryAllSQLLimitPage = "SELECT s.pv,s.uv,s.event_code,d.event_name FROM dd_event_stat s "
            + "LEFT JOIN dd_event_dic d ON s.app_id=d.app_id and s.event_code=d.event_code "
            + "WHERE s.stat_time=? and s.app_id=? and s.dimension_type=? and s.dimension_name=? ORDER BY pv DESC limit ?,?";
        Object[] params = new Object[] {date, appId, dimensionType, version, iDisplayStart, iDisplayLength};
        List<Event> list = douguodataJdbcTemplate.query(queryAllSQLLimitPage, params, new EventRowMapper());
        return list;
    }

    public List<Event> queryAllLimitPage(String appId, String dimensionType, String eventCode, String paramKey,
        String date, int iDisplayStart, int iDisplayLength) {
        String queryAllSQLLimitPage;
        Object[] params;
        if (dimensionType.equals(ConstantStr.PARAM_TYPE_ALL)) {
            queryAllSQLLimitPage = "select a.event_code,a.pv,a.uv,b.event_name,a.param_key, a.param_value " + "from ("
                + "    select event_code,param_key, param_value,pv,uv,app_id from dd_event_param_stat where dimension_type=? and  app_id=? and stat_time=?  order by pv desc limit ?,?"
                + ") a left join ( " + "    select event_name,app_id,event_code from dd_event_dic"
                + ") b on a.app_id=b.app_id and a.event_code=b.event_code";
            params = new Object[] {ConstantStr.PARAM_TYPE_ALL, appId, date, iDisplayStart, iDisplayLength};
        } else if (dimensionType.equals(ConstantStr.PARAM_TYPE_KEY)) {
            queryAllSQLLimitPage =
                "select '1' as event_name,event_code,param_key,param_value,pv,uv from dd_event_param_stat where dimension_type=? and app_id=? and event_code=? and stat_time=? order by pv desc limit ?,?";
            params = new Object[] {ConstantStr.PARAM_TYPE_KEY, appId, eventCode, date, iDisplayStart, iDisplayLength};
        } else {
            queryAllSQLLimitPage =
                "select '1' as event_name,event_code,param_key, param_value,pv,uv from dd_event_param_stat where dimension_type=? and  app_id=? and event_code=? and param_key=? and stat_time=?  order by pv desc limit ?,?";
            params = new Object[] {ConstantStr.PARAM_TYPE_VALUE, appId, eventCode, paramKey, date, iDisplayStart,
                iDisplayLength};
        }
        List<Event> list = douguodataJdbcTemplate.query(queryAllSQLLimitPage, params, new EventRowMapper());
        return list;
    }

    public List<Event> querySearch(String appId, String dimensionType, String eventCode, String paramKey,
        String date,String search){
        String queryAllSQLLimitPage;
        Object[] params;
        if (dimensionType.equals(ConstantStr.PARAM_TYPE_ALL)) {
            queryAllSQLLimitPage = "select a.event_code,a.pv,a.uv,b.event_name,a.param_key, a.param_value " + "from ("
                + "    select event_code,param_key, param_value,pv,uv,app_id from dd_event_param_stat where dimension_type=? and  app_id=? and stat_time=? and event_code like ? order by pv desc "
                + ") a left join ( " + "    select event_name,app_id,event_code from dd_event_dic"
                + ") b on a.app_id=b.app_id and a.event_code=b.event_code";
            params = new Object[] {ConstantStr.PARAM_TYPE_ALL, appId, date,search};
        } else if (dimensionType.equals(ConstantStr.PARAM_TYPE_KEY)) {
            queryAllSQLLimitPage =
                "select '' as event_name,event_code,param_key,param_value,pv,uv from dd_event_param_stat where dimension_type=? and app_id=? and event_code=? and stat_time=?  and param_key like ? order by pv desc ";
            params = new Object[] {ConstantStr.PARAM_TYPE_KEY, appId, eventCode, date,search};
        } else {
            queryAllSQLLimitPage =
                "select '' as event_name,event_code,param_key, param_value,pv,uv from dd_event_param_stat where dimension_type=? and  app_id=? and event_code=? and param_key=? and stat_time=? and param_value like ? order by pv desc ";
            params = new Object[] {ConstantStr.PARAM_TYPE_VALUE, appId, eventCode, paramKey, date,search};
        }
        List<Event> list = douguodataJdbcTemplate.query(queryAllSQLLimitPage, params, new EventRowMapper());
        return list;

    }

    private class EventRowMapper implements RowMapper<Event> {
        @Override public Event mapRow(ResultSet rs, int rowNum) throws SQLException {

            Event event = new Event();
            event.setPv(rs.getInt("pv"));
            event.setUv(rs.getInt("uv"));
            event.setEventCode(rs.getString("event_code"));
            event.setEventName(rs.getString("event_name"));
            event.setParamKey(rs.getString("param_key"));
            event.setParamValue(rs.getString("param_value"));
            return event;
        }
    }
}
