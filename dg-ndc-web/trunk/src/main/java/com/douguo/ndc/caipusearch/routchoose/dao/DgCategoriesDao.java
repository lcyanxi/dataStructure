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
@Repository
public class DgCategoriesDao {
    @Autowired
    @Qualifier("dg2010JdbcTemplate")
    protected JdbcTemplate dg2010jdbcTemplate;

    public List<Map<String,Object>>  queryDgCategories(){
        String sql="select name from dg_categories where  flag>=0";
        Object[] params = new Object[] {};
        return dg2010jdbcTemplate.queryForList(sql,params);
    }
}
