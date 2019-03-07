package com.douguo.ndc.dao;

import com.douguo.ndc.model.TencentStat;
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
 * Date: 2019/1/15
 */
@Repository
public class TencentStatDao {
    @Autowired protected JdbcTemplate secondaryJdbcTemplate;

    public List<TencentStat> queryAllLimitPage(int iDisplayStart, int iDisplayLength) {
        String queryAllSQLLimitPage = "SELECT * FROM dd_tencent_tag_stat order by create_time desc,id DESC limit ?,?";
        Object[] params = new Object[] {iDisplayStart, iDisplayLength};
        List<TencentStat> list = secondaryJdbcTemplate.query(queryAllSQLLimitPage, params, new TencentStatRowMapper());
        return list;
    }

    public List<TencentStat> queryForName(String name,String tagCode) {
        String query = "SELECT * FROM dd_tencent_tag_stat where name=? or tag_code=?";
        Object[] params = new Object[] {name,tagCode};
        List<TencentStat> list = secondaryJdbcTemplate.query(query, params, new TencentStatRowMapper());
        return list;
    }

    public boolean insertTag(TencentStat stat) {
        String insertSQL = "insert into  dd_tencent_tag_stat (name,tag_code,tag_id,create_time,parent_tag_id) VALUES (?,?,?,?,?)";
        int n = secondaryJdbcTemplate.update(insertSQL,stat.getName(),stat.getTagCode(),stat.getTagId(),stat.getCreateTime(),stat.getParentTagId());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTag(String id) {
        String deleteSQL = "delete from dd_tencent_tag_stat where tag_id=?";
        int n = secondaryJdbcTemplate
            .update(deleteSQL,id);
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<TencentStat> querySsearch(String sSearch) {
        String sSearchSQL =
            "select * from dd_tencent_tag_stat where name LIKE '%" + sSearch + "%' or tag_id like '%" + sSearch
                + "%' order by id DESC ";
        List<TencentStat> list = secondaryJdbcTemplate.query(sSearchSQL, new TencentStatRowMapper());
        return list;
    }

    private class TencentStatRowMapper implements RowMapper<TencentStat> {
        @Override public TencentStat mapRow(ResultSet rs, int rowNum) throws SQLException {
            TencentStat stat=new TencentStat();
            stat.setId(rs.getInt("id"));
            stat.setName(rs.getString("name"));
            stat.setTagCode(rs.getString("tag_code"));
            stat.setTagId(rs.getString("tag_id"));
            stat.setParentTagId(rs.getString("parent_tag_id"));
            return stat;
        }
    }
}
