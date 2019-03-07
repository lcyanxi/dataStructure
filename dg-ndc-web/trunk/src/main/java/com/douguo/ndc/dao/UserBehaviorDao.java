package com.douguo.ndc.dao;

import com.douguo.ndc.util.Util;
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
 * Date: 2018/12/27
 */
@Repository("userBehaviorDao")
public class UserBehaviorDao {
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> querySumListByQtype(String startDate, String endDate, String qtype) {
        String sql = "SELECT keyword,sum(nums) as counts FROM `dd_user_behavior_stat` WHERE qtype=? AND statdate BETWEEN ? AND ? GROUP BY keyword ORDER BY counts desc";
        Util.showSQL(sql, new Object[] { qtype, startDate, endDate });
        List<Map<String, Object>> rowlst = jdbcTemplate.queryForList(sql, new Object[] { qtype,startDate, endDate });
        return rowlst;
    }

    public List<Map<String, Object>> querySumListByQtype(String startDate, String endDate, String qtype,String qtype2) {
        String sql = "SELECT keyword,sum(nums) as counts FROM `dd_user_behavior_stat` WHERE qtype in (?,?) AND statdate BETWEEN ? AND ? GROUP BY keyword ORDER BY counts desc";
        Util.showSQL(sql, new Object[] { qtype,qtype2, startDate, endDate });
        List<Map<String, Object>> rowlst = jdbcTemplate.queryForList(sql, new Object[] { qtype,qtype2, startDate, endDate });
        return rowlst;
    }
}