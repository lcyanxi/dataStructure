package com.douguo.ndc.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.dao.DauStatDao;
import com.douguo.ndc.dao.MauStatDao;
import com.douguo.ndc.dao.UserRetainDao;
import com.douguo.ndc.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.douguo.ndc.util.ConstantStr.DATE;
import static com.douguo.ndc.util.ConstantStr.MONTH;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/19
 */
@Service public class UserRetainService {
    /**
     * stat_key_id:
     * 4:新增用户
     * 5:活跃用户
     * 6:菜谱
     * 7:课堂
     * 8:笔记
     * 9:电商
     * 18：新增日留存
     * 17：原始渠道新增留存
     * 58：活跃用户新增留存
     * 38：新增月留存
     * 39：活跃用户月留存
     * <p>
     * <p>
     * 81：菜谱-日
     * 82：电商-日
     * 83：课堂-日
     * 84：笔记-日
     * <p>
     * 91：菜谱-月
     * 92：电商-月
     * 93：课堂-月
     * 94：笔记-月
     */
    private static final String NEW_USER = "new_user";
    private static final String DAY_LIVE = "day_live";
    private static final String COOK = "cook";
    private static final String LIVE = "live";
    private static final String NOTE = "note";
    private static final String MALL = "mall";

    @Autowired private UserRetainDao userRetainDao;

    @Autowired private DauStatDao dauStatDao;

    @Autowired private MauStatDao mauStatDao;

    /**
     * 获取 stat_key_id 编码
     *
     * @param type     类型
     * @param timeType 日 或 月 类型
     * @return stat_key_id
     */
    private Map<String, Integer> getStatKeyID(String type, String timeType) {
        Map<String, Integer> map = new HashMap<>(16);
        int statKeyID;
        int curKeyID;
        if (type.equals(NEW_USER) && timeType.equals(DATE)) {
            statKeyID = 18;
            curKeyID = 4;
        } else if (type.equals(NEW_USER) && timeType.equals(MONTH)) {
            statKeyID = 38;
            curKeyID = 4;
        } else if (type.equals(DAY_LIVE) && timeType.equals(DATE)) {
            statKeyID = 58;
            curKeyID = 5;
        } else if (type.equals(DAY_LIVE) && timeType.equals(MONTH)) {
            statKeyID = 39;
            curKeyID = 5;
        } else if (type.equals(COOK) && timeType.equals(DATE)) {
            statKeyID = 81;
            curKeyID = 6;
        } else if (type.equals(COOK) && timeType.equals(MONTH)) {
            statKeyID = 91;
            curKeyID = 6;
        } else if (type.equals(LIVE) && timeType.equals(DATE)) {
            statKeyID = 83;
            curKeyID = 7;
        } else if (type.equals(LIVE) && timeType.equals(MONTH)) {
            statKeyID = 93;
            curKeyID = 7;
        } else if (type.equals(NOTE) && timeType.equals(DATE)) {
            statKeyID = 84;
            curKeyID = 8;
        } else if (type.equals(NOTE) && timeType.equals(MONTH)) {
            statKeyID = 94;
            curKeyID = 8;
        } else if (type.equals(MALL) && timeType.equals(DATE)) {
            statKeyID = 82;
            curKeyID = 9;
        } else {
            statKeyID = 92;
            curKeyID = 9;
        }
        map.put("curKeyID", curKeyID);
        map.put("statKeyID", statKeyID);
        return map;
    }

    public String queryUserRetain(String appID, String type, String startDate, String endDate, String timeType) {
        if (timeType.equals(MONTH)) {
            startDate = startDate.substring(0, 8) + "01";
            endDate = endDate.substring(0, 8) + "31";
        }
        //获取stat_key_id 编码
        int curKeyID = getStatKeyID(type, timeType).get("curKeyID");
        int statKeyID = getStatKeyID(type, timeType).get("statKeyID");

        // 获取当天日期的数据
        List<Map<String, Object>> curList;

        //日活当日数据和日活当月数据单独在一个地方取
        if (type.equals(DAY_LIVE) && timeType.equals(DATE)) {
            curList = dauStatDao.queryDauStat(appID, startDate, endDate);
        } else if (type.equals(DAY_LIVE) && timeType.equals(MONTH)) {
            curList = mauStatDao.queryMauStat(appID, startDate, endDate);
        } else {
            curList = userRetainDao.queryCurData(appID, startDate, endDate, curKeyID, timeType);
        }

        // 获取之后几天的数据
        List<Map<String, Object>> list = userRetainDao.queryUserRetain(appID, startDate, endDate, statKeyID, timeType);
        return userRetainUtil(curList, list, timeTypeUtils(timeType), type, timeType);

    }

    /**
     * @param curList      当天的新增数据
     * @param list         留存率的数据
     * @param timeTypeList 存放要展示的数据集字段
     * @param timeType     类型 日或者月
     * @return 返回json字符串
     */
    private String userRetainUtil(List<Map<String, Object>> curList, List<Map<String, Object>> list,
        List<String> timeTypeList, String type, String timeType) {
        if (curList.isEmpty()) {
            return "";
        }
        //用于存放每一列的总数
        Object[] objects;
        //用于存放每一列的平均天数
        Integer[] avergNum;
        if (timeType.equals(DATE)) {
            objects = new Object[11];
            avergNum=new Integer[11];
        } else {
            objects = new Object[14];
            avergNum=new Integer[14];
        }
        objects[0] = "均值";
        //objects[1] = "---";
        BigDecimal num=new BigDecimal(0);
        JSONArray jsonArray = new JSONArray();
        // 遍历当天的数据集
        for (Map<String, Object> curMap : curList) {
            List<Object> tmp = new ArrayList<>();
            String statdate;
            if (timeType.equals(DATE)) {
                statdate = DateUtil.dateToString((Date)curMap.get("statdate"), "yyyy-MM-dd");
            } else {
                statdate = (String)curMap.get("months");
            }
            BigDecimal statValue = (BigDecimal)curMap.get("stat_value");
            tmp.add(statdate);
            tmp.add(statValue);
            num=statValue.add(num);

            int index = 2;
            // 遍历要展示填充字段的数据集
            for (String str : timeTypeList) {
                JSONObject object = new JSONObject();
                boolean flag = true;
                //遍历留存率数据集
                for (Map<String, Object> map : list) {
                    String tmpDate;
                    if (timeType.equals(DATE)) {
                        tmpDate = DateUtil.dateToString((Date)map.get("statdate"), "yyyy-MM-dd");
                    } else {
                        tmpDate = (String)map.get("months");
                    }
                    if (tmpDate.equals(statdate) && str.equals(map.get("time_type"))) {
                        Double bigDecimal = (Double)map.get("stat_value");
                        BigDecimal target = new BigDecimal((bigDecimal / statValue.doubleValue()) * 100);
                        bigDecimal = target.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        //数据渲染颜色
                        object.put("color", dataColorUtils(bigDecimal));
                        object.put("data", bigDecimal + "%");
                        //每一列汇总  用于求均值
                        if (objects[index] == null) {
                            objects[index] = bigDecimal;
                            avergNum[index]=1;
                        } else {
                            objects[index] = (double)objects[index] + bigDecimal;
                            avergNum[index]=avergNum[index]+1;
                        }
                        index++;
                        tmp.add(object);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    object.put("color", "");
                    object.put("data", "---");
                    tmp.add(object);
                    if (objects[index] == null) {
                        objects[index] = 0.0;
                        avergNum[index]=0;
                    }
                    index++;
                }
            }
            jsonArray.add(tmp);
        }

        //计算第一例新增用户的平均值

        BigDecimal curNum = new BigDecimal((num.doubleValue()/ curList.size()));
        objects[1]=curNum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        // 计算每一列的平均值
        for (int i = 2; i < objects.length; i++) {
            JSONObject object = new JSONObject();
            if (objects[i] == null || (double)objects[i] == 0 || objects[i] == "") {
                object.put("color", "");
                object.put("data", "---");
                objects[i] = object;
                continue;
            }
            BigDecimal target = new BigDecimal(((double)objects[i] / avergNum[i]));
            double tmp = target.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (tmp == 0.0) {
                object.put("color", "");
                object.put("data", "---");
                objects[i] = object;
            } else {
                object.put("color", "");
                object.put("data", tmp + "%");
                objects[i] = object;
            }

        }

        jsonArray.add(0, Arrays.asList(objects));
        return jsonArray.toJSONString();
    }

    /**
     * 封装日和月要展示的数据结果类型
     *
     * @param type 类型 日  或者 月
     * @return 返回一个集合
     */
    private List<String> timeTypeUtils(String type) {
        List<String> timeType = new ArrayList<>();
        timeType.add("LAST1");
        timeType.add("LAST2");
        timeType.add("LAST3");
        timeType.add("LAST4");
        timeType.add("LAST5");
        timeType.add("LAST6");
        timeType.add("LAST7");
        if (type.equals(DATE)) {
            timeType.add("LAST14");
            timeType.add("LAST30");
        } else if (type.equals(MONTH)) {
            timeType.add("LAST8");
            timeType.add("LAST9");
            timeType.add("LAST10");
            timeType.add("LAST11");
            timeType.add("LAST12");
        }
        return timeType;
    }

    /**
     * 通过数据给td 渲染颜色
     *
     * @param data 留存率数据
     * @return td 颜色
     */
    private String dataColorUtils(double data) {

        String tmp = "";
        if (data < 20) {
            tmp = "style=\"background: #b6f2f3\"";

        } else if (data >= 20 && data < 40) {
            tmp = "style=\"background: #8ee6ea\"";
        } else if (data >= 40) {
            tmp = "style=\"background: #5dcacf\"";
        }
        return tmp;
    }

    /**
     * @param size     查询的天数
     * @param index    要展示的第几个字段
     * @param timeType 类型 日或者月
     * @return 平均数要除以的天数
     */
    private int avergNumUtil(int size, int index, String type, String timeType) {
        if (size == 1) {
            return 1;
        }
        if (timeType.equals(DATE)) {
            if (index == 2) {
                return size - 1;
            } else if (index == 3) {
                return size - 2;
            } else if (index == 4) {
                return size - 3;
            } else if (index == 5) {
                return size - 4;
            } else if (index == 6) {
                return size - 5;
            } else if (index == 7) {
                return size - 6;
            } else if (index == 8) {
                return size - 7;
            } else if (index == 9) {
                return size - 14;
            } else {
                return size - 30;
            }
        } else {
            // 日活拿不到当月的数据
            if (type.equals(DAY_LIVE)) {
                return size - (index - 1);
            }
            return size - index;
        }
    }

}
