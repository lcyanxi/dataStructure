package com.douguo.ndc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/2
 */
@Repository("douguoExtDao") public class DouguoExtDao {
    private static final String ALL = "all";

    @Autowired @Qualifier("secondaryJdbcTemplate") private JdbcTemplate douguodataJdbcTemplate;

    public List<Map<String, Object>> queryDouguoExt(String type) {
        String sql;
        Object[] params;
        if (type.equals(ALL)) {
            sql = "select * from dd_douguo_ext_dic where flag=0 ORDER by id desc limit 1";
            params = new Object[] {};
        } else {
            sql = "select * from dd_douguo_ext_dic where flag=0 and type=? ORDER by id desc limit 1";
            params = new Object[] {type};
        }

        List<Map<String, Object>> list = douguodataJdbcTemplate.queryForList(sql, params);
        return list;
    }

    public int queryCount(String type) {
        String sql;
        if (type.equals(ALL)) {
            sql = "select count(*) from dd_douguo_ext_dic where flag=0 ";
        } else {
            sql = "select count(*) from dd_douguo_ext_dic where flag=0 and type='"+type+"'";
        }

        int num = douguodataJdbcTemplate.queryForObject(sql, Integer.class);
        return num;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, Object>> updateDouguoExt(int id, int flag, String type) {
        String updateSql = "update dd_douguo_ext_dic set flag=? where id=?";
        Object[] updateparams = new Object[] {flag, id};
        int n = douguodataJdbcTemplate.update(updateSql, updateparams);
        return queryDouguoExt(type);
    }
}
