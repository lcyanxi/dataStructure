package com.douguo.ndc.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/28
 */
public class QueryTableUtil {

    /**
     * 获取query table 的请求参数
     *
     * @param aoData
     * @return 封装table请求过来的参数
     */
    public static Map getQueryTableParms(String aoData) {
        JSONArray jsonarray = JSONArray.fromObject(aoData);
        //记录操作的次数
        String sEcho = null;
        // 起始索引
        int iDisplayStart = 0;
        // 每页显示的行数
        int iDisplayLength = 0;

        String sSearch = null;

        //这里获取从前台传递过来的参数，从而确保是否分页、是否进行查询、是否排序等
        for (int i = 0; i < jsonarray.size(); i++) {
            JSONObject obj = (JSONObject)jsonarray.get(i);
            if (obj.get("name").equals(ConstantStr.SECHO)) {
                sEcho = obj.get("value").toString();
            }

            if (obj.get("name").equals(ConstantStr.IDISPLAYSTART)) {
                iDisplayStart = obj.getInt("value");
            }

            if (obj.get("name").equals(ConstantStr.IDISPLAYLENGTH)) {
                iDisplayLength = obj.getInt("value");
            }
            if (obj.get("name").equals(ConstantStr.SSEARCH)) {
                sSearch = obj.get("value").toString();
            }
        }

        Map map = new HashMap(16);

        map.put(ConstantStr.SECHO, sEcho);
        map.put(ConstantStr.IDISPLAYSTART, iDisplayStart);
        map.put(ConstantStr.IDISPLAYLENGTH, iDisplayLength);
        map.put(ConstantStr.SSEARCH, sSearch);
        return map;

    }

    /**
     * query table 用于展示table封装返回参数
     *
     * @param list      数据
     * @param totalSize 总条数
     * @return
     */

    public static com.alibaba.fastjson.JSONObject converToJSONObject(List list, int totalSize) {
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put(ConstantStr.ITOTALDISPLAYRECORDS, totalSize);
        jsonObject.put(ConstantStr.ITOTALRECORDS, totalSize);
        jsonObject.put(ConstantStr.AADATA, list);
        return jsonObject;
    }

    /**
     * query table 用于页面搜索框封装查询返回参数
     *
     * @param list 数据
     * @return
     */
    public static com.alibaba.fastjson.JSONObject converToJSONObject(List list) {
        int totalSize = list.size();
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put(ConstantStr.ITOTALDISPLAYRECORDS, totalSize);
        jsonObject.put(ConstantStr.ITOTALRECORDS, totalSize);
        jsonObject.put(ConstantStr.AADATA, list);
        return jsonObject;
    }

}
