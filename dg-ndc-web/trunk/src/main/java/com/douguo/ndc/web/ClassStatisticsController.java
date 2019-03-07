package com.douguo.ndc.web;

import com.douguo.ndc.model.LiveClass;
import com.douguo.ndc.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.douguo.ndc.util.ProfileUtil.ALL;

@Controller @RequestMapping("/class") public class ClassStatisticsController {

        @GetMapping(value = "/topN") public String to_page() {
            return "pages/class_topN";
        }

        @RequestMapping(value = "/queryTopN/{type}", method = RequestMethod.GET) @ResponseBody
        public Map queryNextProfile(@PathVariable(value = "type") String type, String beginDate, String endDate, String sort, Integer topN) {
            //接收参数处理
            Map<String, Object> map = new HashMap<>(16);
            if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
                beginDate = "1970-01-01";
                endDate = DateUtil.getDate();

            }
            if(topN == null){
                topN = 10;
            }
            if(StringUtils.isBlank(type)){
                type = "class";
            }
            if("class".equals(type)){
                map.put("nextTitle",beginDate+"至"+endDate+" 课堂 销售Top"+topN+"统计");
            }
            if("teacher".equals(type)){
                map.put("nextTitle",beginDate+"至"+endDate+" 老师 销售Top"+topN+"统计");
            }

            //数据获取
            LiveClass[] liveclasslist = new LiveClass[topN];
            System.out.println(type);
            System.out.println(beginDate);
            System.out.println(endDate);
            System.out.println(topN);
            System.out.println(sort);
            try {
                Class.forName("org.apache.hive.jdbc.HiveDriver");
                Connection con = DriverManager.getConnection("jdbc:hive2://192.168.1.162:10000/default", "dg-hadoop", "");
                Statement stmt = con.createStatement();
                String querySQL = null;
                ResultSet res =null;
                if("class".equals(type)){
                    querySQL=   "SELECT a.class_id AS class_id ,b.title AS title ,a.sales AS sales " +
                                " FROM(" +
                                "      SELECT sales , class_id" +
                                "      FROM(" +
                                "              SELECT count(user_id) AS sales ,class_id" +
                                "              FROM dg_live_enroll" +
                                "              WHERE status = 2  AND to_date(paytime) >= '"+beginDate+"' AND to_date(paytime) <= '"+endDate+"' AND paytype<>'couponNoSettlement' AND isrobot = 0" +
                                "              GROUP BY class_id" +
                                "      ) c ORDER BY sales "+sort+" LIMIT " + topN +
                                " ) a JOIN dg_live_class b ON a.class_id= b.id "+
                                " ORDER BY a.sales "+sort+" LIMIT " +topN;

                    res = stmt.executeQuery(querySQL); // 执行查询语句
                    int i=0;
                    while(res.next()){
                        int classid = Integer.parseInt(res.getString("class_id"));
                        String title = res.getString("title");
                        int sales = Integer.parseInt(res.getString("sales"));
                        System.out.println(classid);
                        System.out.println(title);
                        System.out.println(sales);
                        LiveClass liveclass = new LiveClass();
                        liveclass.setClassId(classid);
                        liveclass.setTitle(title);
                        liveclass.setSales(sales);
                        liveclasslist[i++] = liveclass;
                    }

                }

                if("teacher".equals(type)){
                    querySQL=   "SELECT d.user_id AS user_id ,d.nickname AS nickname,c.sales AS sales " +
                                " FROM(" +
                                "      SELECT b.user_id AS user_id, sum(a.sales) AS sales" +
                                "      FROM(" +
                                "              SELECT count(user_id) AS sales ,class_id" +
                                "              FROM dg_live_enroll" +
                                "              WHERE status = 2  AND to_date(paytime) >= '"+beginDate+"' AND to_date(paytime) <= '"+endDate+"' AND paytype<>'couponNoSettlement' AND isrobot = 0" +
                                "              GROUP BY class_id" +
                                "      ) a JOIN dg_live_class b on a.class_id = b.id" +
                                "      GROUP BY b.user_id" +
                                "      ORDER BY sales "+sort+" limit " + topN +
                                " )c JOIN dg_user d ON c.user_id=d.user_id " +
                                " ORDER BY sales "+sort+" LIMIT " +topN;
                    res = stmt.executeQuery(querySQL); // 执行查询语句
                    int i=0;
                    while(res.next()){
                        int classid = Integer.parseInt(res.getString("user_id"));
                        String nickname = res.getString("nickname");
                        int sales = Integer.parseInt(res.getString("sales"));
                        LiveClass liveclass = new LiveClass();
                        liveclass.setClassId(classid);
                        liveclass.setTitle(nickname);
                        liveclass.setSales(sales);
                        liveclasslist[i++] = liveclass;
                    }
                }
                closeConnection(con,stmt,res);
            } catch (Exception e) {
                e.printStackTrace();
            }


            map.put("tablejson",liveclasslist);
            return map;
        }

    @RequestMapping(value = "/exportExcel/{type}", method = RequestMethod.GET)
    public ModelAndView exportExcel(@PathVariable(value = "type") String type, String beginDate, String endDate, String sort, Integer topN,
                                    ModelMap model) {

        return new ModelAndView(new ProfileExcelUtil(), model);
    }

    public void closeConnection(Connection conn,Statement stat,ResultSet rs){
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    rs = null;
                }
            }

            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    stat = null;
                }
            }

            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    conn = null;
                }
            }
    }

}
