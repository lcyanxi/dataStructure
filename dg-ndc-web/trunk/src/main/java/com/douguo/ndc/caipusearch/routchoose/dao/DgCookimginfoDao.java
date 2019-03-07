package com.douguo.ndc.caipusearch.routchoose.dao;

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
 * Date: 2018/12/12
 */
@Repository public class DgCookimginfoDao {
    @Autowired @Qualifier("dg2010JdbcTemplate") protected JdbcTemplate dg2010jdbcTemplate;

    public List<Map<String, Object>> queryDgCookimginfo(List cooks) {
        String target = "";
        for (int i = 0; i < cooks.size(); i++) {
            target = target + "?,";
        }
        target=target.substring(0,target.length() - 1);
        String sql = "select * from dg_cookimginfo where cook_id in ("+target+")";
        System.out.println(target);
        return dg2010jdbcTemplate.queryForList(sql, cooks);
    }

    public List<Map<String, Object>> queryDgCookimginfo(String  cookId) {
        String sql = "select * from dg_cookimginfo where cook_id=?";
        Object[] params = new Object[] {cookId};
        return dg2010jdbcTemplate.queryForList(sql,params);
    }
}
