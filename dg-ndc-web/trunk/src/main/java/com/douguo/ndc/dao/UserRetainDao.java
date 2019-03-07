package com.douguo.ndc.dao;

import com.douguo.ndc.util.ConstantStr;
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
 * Date: 2018/11/19
 */
@Repository public class UserRetainDao {
    private static final String ZERO="0";

    @Autowired @Qualifier("secondaryJdbcTemplate") private JdbcTemplate douguodataJdbcTemplate;

    public List<Map<String, Object>> queryUserRetain(String appId, String startDate, String endDate, int statKeyId,String timeType) {
        String sql;
        String client="client=? and";
        Object[] params = new Object[] {appId,statKeyId,startDate,endDate};
        if(appId.equals(ZERO)){
            client="client in(3,4) AND ";
            params = new Object[] {statKeyId,startDate,endDate};
        }
        if (timeType.equals(ConstantStr.DATE)){
            sql ="select statdate ,time_type,sum(stat_value) as  stat_value from dd_app_user_retained_stat where "+client+" stat_key_id =? and dimension_type='ALL' and  statdate between ? and ? group by statdate,time_type order by statdate ,time_type";
        }else {
            sql= "select DATE_FORMAT(statdate,'%Y%m') months ,time_type,sum(stat_value) as  stat_value from dd_app_user_retained_stat where "+client+"  dimension_type='ALL' and stat_key_id =? and  statdate between ? and ? group by months,time_type order by months,time_type";

        }

        return douguodataJdbcTemplate.queryForList(sql,params);
    }

    public List<Map<String,Object>> queryCurData(String appId,String startDate,String endDate,int statKeyId,String timeType){
        String client="and client=?";
        Object[] params = new Object[] {startDate,endDate,statKeyId,appId};
        if(appId.equals(ZERO)){
            client=" and client in(3,4)  ";
            params = new Object[] {startDate,endDate,statKeyId};
        }
        String sql;
        if (timeType.equals(ConstantStr.DATE)){
            sql="select statdate,sum(stat_value) as stat_value from dd_app_collection_stat where statdate between ? and ? and time_type='TODAY' and dimension_type='ALL' and  stat_key_id=?  "+client+" group by statdate order by statdate ";
        }else {
            sql="select DATE_FORMAT(statdate,'%Y%m') months,sum(stat_value) as stat_value from dd_app_collection_stat where statdate between ? and ? and time_type='TODAY' and dimension_type='ALL' and  stat_key_id=?    "+client+" group by months  order by  months ";
        }

        return douguodataJdbcTemplate.queryForList(sql,params);
    }
}
