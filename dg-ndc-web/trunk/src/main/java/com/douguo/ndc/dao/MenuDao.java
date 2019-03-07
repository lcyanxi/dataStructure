package com.douguo.ndc.dao;

import com.douguo.ndc.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/9/28
 */
@Repository
public class MenuDao {

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private   JdbcTemplate douguodataJdbcTemplate;

    /**
     * 树形菜单展示数据
     * @param uid
     * @return
     */
    public List<Map<String,Object>> queryMenus(String uid){
        String sql =
            "select b.*"
                + "from ("
                    + "   select menu_id  "
                    + "   from tc_user_menu   "
                    + "   where user_id=?"
                + ")a join ( "
                     + "select *   from tc_menu_new where visiable=1"
                + ") b on a.menu_id=b.id "
                + " order by b.pId , b.sortId ";
        Object[] params = new Object[] { uid };
        List<Map<String,Object>> list = douguodataJdbcTemplate.queryForList(sql,params);
        return list;
    }


    public List<Map<String,Object>> queryMenusTree(){
        String sql ="select * from tc_menu_new order by sortId";
        Object[] params = new Object[] {};
        List<Map<String,Object>> list = douguodataJdbcTemplate.queryForList(sql,params);
        return list;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean addMenuNode(Menu menu){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql="insert into  tc_menu_new (pId,name,url,has_child,icon,type,visiable) VALUES (?,?,?,?,?,?,?)";
        // 主要是拿到自动生成的id
        douguodataJdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,menu.getpId());
                ps.setString(2, menu.getName());
                ps.setString(3, menu.getUrl());
                ps.setInt(4,menu.getHasChild());
                ps.setString(5, menu.getIcon());
                ps.setInt(6,menu.getType());
                ps.setInt(7,menu.getVisiable());
                return ps;
            }
        }, keyHolder);

        String updateSql="update tc_menu_new set sortId=? where id=?";
        int n= douguodataJdbcTemplate.update(updateSql,keyHolder.getKey().intValue(),keyHolder.getKey().intValue());

        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 菜单权限查询
     * @param uid
     * @return
     */
    public List<Map<String,Object>> queryMenusPermission(String uid){
        String sql ="select a.*,if(b.id is null,0,1) as sortId  "
            + "from ("
            + "   select *"
            + "   from tc_menu_new"
            + ") a left join ("
            + "   select menu_id,id   from tc_user_menu"
            + "     where user_id=? "
            + ") b on b.menu_id=a.id"
            + "  order by a.pId , a.sortId ";
        Object[] params = new Object[] { uid };
        List<Map<String,Object>> list = douguodataJdbcTemplate.queryForList(sql,params);

        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMenuNode(String id){
        final String delSql="delete from tc_menu_new where id=?";
        Object[] deparams = new Object[] { id };
        douguodataJdbcTemplate.update(delSql,deparams);
        final String del="delete from tc_user_menu where menu_id=?";
        Object[] params = new Object[] { id };
        douguodataJdbcTemplate.update(del,params);

        return true;
    }


    public boolean renameMenuNode(String id,String newName){
        final String delSql="update  tc_menu_new set name=? where id=?";
        Object[] deparams = new Object[] { newName,id};
        douguodataJdbcTemplate.update(delSql,deparams);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenusPermission(List<String> nodes,String uid){
        final String delSql="delete from tc_user_menu where user_id=?";
        Object[] deparams = new Object[] { uid };

        douguodataJdbcTemplate.update(delSql,deparams);

        List<Object[]> params = new ArrayList<>();
        for(String i:nodes) {
            params.add(new Object[]{i,uid});
        }
        final String sql = "insert into tc_user_menu(menu_id,user_id) values(?,?)";
        douguodataJdbcTemplate.batchUpdate(sql, params);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenuLocation(String id,String pid){
        final String updateSql="update  tc_menu_new set pid=? where id=?";
        Object[] deparams = new Object[] { pid,id };
       int target= douguodataJdbcTemplate.update(updateSql,deparams);
        final String sql="update  tc_menu_new set has_child=1 where id=?";
        Object[] params = new Object[] { pid };
        int i= douguodataJdbcTemplate.update(sql,params);
        if (target>0 && i>0){
            return true;
        }else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenuLocation(String ids){
        final String updateSql="update  tc_menu_new set sortId=? where id=?";
        String[]  nodesArray = ids.split(",");
        List<String> list= Arrays.asList(nodesArray);
        List<Object[]> params = new ArrayList<>();
        int sortId=1;
        for(String i:list) {
            params.add(new Object[]{sortId,i});
            sortId++;
        }
        int[] target= douguodataJdbcTemplate.batchUpdate(updateSql, params);
        if (target.length>0){
            return true;
        }else {
            return false;
        }
    }

}
