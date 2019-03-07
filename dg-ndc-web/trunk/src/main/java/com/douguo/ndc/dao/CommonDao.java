package com.douguo.ndc.dao;

import com.douguo.ndc.util.ConstantStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/12
 */
@Repository public class CommonDao {
    @Autowired @Qualifier("secondaryJdbcTemplate") private JdbcTemplate douguodataJdbcTemplate;

    public int queryAllCount(String sql) {
        return douguodataJdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int queryParamCount(String dimensionType, String appId, String date, String eventCode, String paramKey) {
        String countSql;
        Object[] params;
        if (dimensionType.equals(ConstantStr.PARAM_TYPE_ALL)) {
            countSql = "select count(*) from dd_event_param_stat where dimension_type=? and stat_time=? and app_id=?";
            params = new Object[] {ConstantStr.PARAM_TYPE_ALL, date, appId};
        } else if (dimensionType.equals(ConstantStr.PARAM_TYPE_KEY)) {
            countSql =
                "select count(*) from dd_event_param_stat where dimension_type=? and stat_time= ? and app_id=? and event_code=?";
            params = new Object[] {ConstantStr.PARAM_TYPE_KEY, date, appId, eventCode};
        } else {
            countSql =
                "select count(*) from dd_event_param_stat where dimension_type=? and stat_time= ? and app_id=? and event_code=? and param_key=?";
            params = new Object[] {ConstantStr.PARAM_TYPE_VALUE, date, appId, eventCode, paramKey};
        }
        return douguodataJdbcTemplate.queryForObject(countSql, params, Integer.class);
    }
}
