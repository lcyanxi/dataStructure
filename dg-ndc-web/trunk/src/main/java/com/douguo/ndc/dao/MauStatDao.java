package com.douguo.ndc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/27
 */
@Repository
public class MauStatDao {
    private static final String ZERO="0";

    @Autowired @Qualifier("secondaryJdbcTemplate") private JdbcTemplate douguodataJdbcTemplate;

    /**
     * 日活当月用户数据
     * @param appId 渠道
     * @param startDate 统计开始日期
     * @param endDate 统计结束日期
     * @return data
     */
    public List<Map<String, Object>> queryMauStat(String appId, String startDate, String endDate) {

        String client="app=? and";
        Object[] params = new Object[] {appId, startDate, endDate};
        if(appId.equals(ZERO)){
            client="app in(3,4) AND ";
            params = new Object[] {startDate, endDate};
        }
        String sql = " SELECT DATE_FORMAT(statdate,'%Y%m') months,sum(uid) as stat_value FROM `dd_mau_stat` WHERE  "+client+" statdate BETWEEN ? AND ? GROUP BY  months  ORDER BY  months";

        return douguodataJdbcTemplate.queryForList(sql, params);
    }
}
