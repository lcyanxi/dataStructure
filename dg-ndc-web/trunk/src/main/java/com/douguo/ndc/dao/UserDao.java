package com.douguo.ndc.dao;

import com.douguo.ndc.model.User;
import com.douguo.ndc.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * Date: 2018/8/22
 */

@Repository
public class UserDao {

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate douguodataJdbcTemplate;

/*    private final String insertUserSQL = "INSERT INTO tc_user(uid,pass,username) values (?,?,?)";
    private final String updateUserSQL = "update tc_user set uid=?,username=? where uid=? ";
    private final String delUserSQL = "delete from tc_user where uid=?";
    private final String updateUserPassSQL = "update tc_user set pass=? where uid=?";*/

    private final String getUserSQL = "SELECT id,uid,pass,type,group_id,username FROM tc_user where uid=? ";

    public List<User> getUserByUid(String uid) {
        Object[] params = new Object[] { uid };
        List<User> listUser = douguodataJdbcTemplate.query(getUserSQL, params, new UserRowMapper());
        return listUser;
    }

    public List<User> getUsers() {
        final String getUserSQL = "SELECT id,uid,pass,type,group_id,username FROM tc_user order by id";
        List<User> listUser = douguodataJdbcTemplate.query(getUserSQL, new UserRowMapper());
        return listUser;
    }

    public List<Map<String,Object>> getMenuLists(String uid){
        String sql="select b.*\n" + "from (\n" + "    select * from tc_user_menu where user_id=?\n"
            + ") a join (\n" + "   select * from tc_menu_new\n" + ") b on a.menu_id=b.id";
        SqlUtil.showSQL(sql, new Object[]{});
        List<Map<String,Object>> menus = douguodataJdbcTemplate.queryForList(sql,new Object[]{uid});
        return menus;

    }

    public List<Map<String,Object>> queryPermission(){
        String sql ="select * from tc_menu_new where type =1";
        Object[] params = new Object[] {};
        List<Map<String,Object>> list = douguodataJdbcTemplate.queryForList(sql,params);
        return list;
    }

    public class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUid(rs.getString("uid"));
            user.setUsername(rs.getString("username"));
            user.setPass(rs.getString("pass"));
            return user;
        }
    }

}
