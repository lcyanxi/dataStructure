package com.douguo.ndc.caipusearch.routadjust.dao;

import com.douguo.ndc.caipusearch.routadjust.model.SearchWordAdjust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/12
 */
@Repository public class SearchWordAdjustDao {
    @Autowired protected JdbcTemplate secondaryJdbcTemplate;

    public List<Map<String, Object>> querySearchWordAdjust() {
        String sql = "select * from dd_searchword_adjust";
        Object[] params = new Object[] {};
        return secondaryJdbcTemplate.queryForList(sql, params);
    }

    public List<SearchWordAdjust> querySearchWordAdjustLimitPage(int iDisplayStart, int iDisplayLength) {
        String sql = "select * from dd_searchword_adjust order by id desc limit ?,?";
        Object[] params = new Object[] {iDisplayStart, iDisplayLength};
        return secondaryJdbcTemplate.query(sql, params, new SearchWordAdjustRowMapper());
    }


    public List<SearchWordAdjust> querySearchWordAdjustSsearch(String sSearch) {
        String sql = "select * from dd_searchword_adjust where key_word like '%"+sSearch+"%' or exclude_key like '%"+sSearch+"'%";
        Object[] params = new Object[] {};
        return secondaryJdbcTemplate.query(sql, params, new SearchWordAdjustRowMapper());
    }

    public boolean insertAdjustWord(SearchWordAdjust adjust) {
        String insertSQL = "insert into  dd_searchword_adjust (key_word,type,exclude_key) VALUES (?,?,?)";
        int n = secondaryJdbcTemplate.update(insertSQL, adjust.getKey(), adjust.getType(), adjust.getExclude_key());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateAdjustWord(SearchWordAdjust adjust) {
        String updateSQL = "update dd_searchword_adjust set type=?,exclude_key=? where id=?";
        int n = secondaryJdbcTemplate.update(updateSQL, adjust.getType(), adjust.getExclude_key(), adjust.getId());
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAdjustWord(String id) {
        final String delSql = "delete from dd_searchword_adjust where id=?";
        int n = secondaryJdbcTemplate.update(delSql, id);
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    private class SearchWordAdjustRowMapper implements RowMapper<SearchWordAdjust> {
        @Override public SearchWordAdjust mapRow(ResultSet rs, int rowNum) throws SQLException {
            SearchWordAdjust searchWordAdjust = new SearchWordAdjust();
            searchWordAdjust.setId(rs.getInt("id"));
            searchWordAdjust.setKey(rs.getString("key_word"));
            searchWordAdjust.setType(rs.getString("type"));
            searchWordAdjust.setExclude_key(rs.getString("exclude_key"));
            return searchWordAdjust;
        }
    }
}
