package com.douguo.ndc.web;

import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.concurrent.*;

import static com.douguo.ndc.util.ProfileUtil.ALL;

/**
 * Created by lichang on 2018/7/30
 */
@RestController public class ProfileController {

    @Autowired private TestEsCliectUtil testEsCliectUtil;

    @RequestMapping(value = "/queryProfile/{type}", method = RequestMethod.GET) @ResponseBody
    public Map queryProfileData(@PathVariable(value = "type") String type, String flag) {

        Map<String, Object> map = new HashMap<>(16);
        long start = System.currentTimeMillis();

        String str =testEsCliectUtil.getNextTags(type, ConstantStr.SIZE).toString();
           // testEsCliectUtil.getRecommendCooks(crowd,type,  ConstantStr.SIZE, beginDate, endDate, isLogin,"0").toString();

        long end = System.currentTimeMillis();
        System.out.println("一级标签一个请求时间：" + (end - start));
        if (type.equals(ProfileUtil.CITY) || type.equals(ProfileUtil.PROVINCE)) {
            map.put(ConstantStr.BARDATA, converBarData(str, flag));
        } else {
            Map<String, String> tempMap = new HashMap<>(16);
            tempMap.put(ConstantStr.NAME, "");
            tempMap.put(ConstantStr.VALUE, "");
            map.put(ConstantStr.BARDATA, tempMap);
        }
        map.put(ConstantStr.DATA, converList(str, "", flag));
        map.put(ConstantStr.TITLE, ProfileUtil.converTitle(type));
        return map;
    }

    @RequestMapping(value = "/queryNextProfile/{crowd}/{parentType}", method = RequestMethod.GET) @ResponseBody
    public Map queryNextProfile(@PathVariable(value = "crowd") String crowd,@PathVariable(value = "parentType") String parentType,
        String type, String beginDate, String endDate, String isLogin, String flag) {

        if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
            beginDate = "1970-01-01";
            endDate = DateUtil.getDate();

        }
        if (parentType.equals(ALL)){
            return nextProfileUtil(crowd,"", "", beginDate, endDate, isLogin, flag);
        }else {
           return nextProfileUtil(crowd,parentType, type, beginDate, endDate, isLogin, flag);
        }

    }

    @RequestMapping(value = "/exportExcel/{crowd}/{parentType}/{type}", method = RequestMethod.GET)
    public ModelAndView exportExcel(@PathVariable(value = "crowd") String crowd,@PathVariable(value = "parentType") String parentType,
        @PathVariable(value = "type") String type, String beginDate, String endDate, String isLogin, String flag,
        ModelMap model) {
        Map map;
        if (parentType.equals(ALL)){
            map=nextProfileUtil(crowd,"", "", beginDate, endDate, isLogin, flag);
        }else {
            map = nextProfileUtil(crowd,parentType, type, beginDate, endDate, isLogin, flag);
        }

        List<Object> list = new ArrayList<>();

        list.add(excelConverDateUtil((Map)map.get(ProfileUtil.SEX)));
        list.add(excelConverDateUtil((Map)map.get(ProfileUtil.GENERATION)));
        list.add(excelConverDateUtil((Map)map.get(ProfileUtil.INCOME)));
        list.add(excelConverDateUtil((Map)map.get(ProfileUtil.PROFESSION)));
        list.add(excelConverDateUtil((Map)map.get("taboos")));
        list.add(excelConverDateUtil((Map)map.get("tags")));
        list.add(excelConverDateUtil((Map)map.get("chron")));
        list.add(excelConverDateUtil((Map)map.get("states")));
        list.add(excelConverDateUtil((Map)map.get(ProfileUtil.PROVINCE)));
        list.add(excelConverDateUtil((Map)map.get(ProfileUtil.CITY)));

        List<String> colTitleList = new ArrayList<>();
        colTitleList.add("ID");
        colTitleList.add("标签名称");
        colTitleList.add("数量");
        colTitleList.add("占比");

        model.put("colTitleList", colTitleList);
        model.put("exclName", ProfileUtil.converTitle(parentType) + "_" + type);
        model.put("list", list);

        return new ModelAndView(new ProfileExcelUtil(), model);
    }

    /**
     * 封装饼图和table表格数据
     *
     * @param jsonStr 返回的json数据
     * @param type    二级标签参数
     * @return map 集合
     */
    private Map converList(String jsonStr, String type, String flag) {
        Map<String, Object> map = new HashMap<>(16);
        JSONObject jsonConfig = JsonUtil.parseStrToJsonObj(jsonStr);

        String sign = "";
        List<Map> tempList = null;
        //解析一级标签的数据
        if (type.equals(sign)) {
            tempList = (List<Map>)jsonConfig.get(ConstantStr.LIST_TAG);
        } else {
            //解析二级标签的数据
            List<Map> tmp = (List<Map>)jsonConfig.get(ConstantStr.LIST_TAG_SUB);
            for (Map indexMap : tmp) {
                if (indexMap.containsKey(type)) {
                    tempList = (List)indexMap.get(type);
                    break;
                }
            }
        }
        if (tempList==null){
            map.put(ConstantStr.DATA, "");
            map.put(ConstantStr.TOTAL, 0);
            return map;
        }

        List<Map> list = new ArrayList<>();
        int total = 0;
        for (Map tmpMap : tempList) {
            Iterator iter = tmpMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next();
                Map<String, Object> map1 = new HashMap<>(16);
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (flag.equals("1") && "".equals(key)) {
                    break;
                }
                if ("".equals(key)) {
                    key = "未知";
                }
                map1.put(ConstantStr.NAME, key);
                map1.put(ConstantStr.VALUE, value);
                list.add(map1);
                total = total + (int)value;
            }
        }
        if (list.isEmpty()) {
            map.put(ConstantStr.DATA, "");
        } else {
            map.put(ConstantStr.DATA, list);
        }
        map.put(ConstantStr.TOTAL, total);
        return map;
    }

    /**
     * 封装柱形图数据格式
     *
     * @param jsonStr json 字符串
     * @return map集合
     */
    private Map<String, Object> converBarData(String jsonStr, String flag) {
        Map<String, Object> map = new HashMap<>(16);
        List<Object> nameList = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        JSONObject jsonConfig = JsonUtil.parseStrToJsonObj(jsonStr);
        List<Map> tempList = (List<Map>)jsonConfig.get(ConstantStr.LIST_TAG);
        for (Map tmpMap : tempList) {
            Iterator iter = tmpMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next();
                Object key = entry.getKey();
                if (flag.equals("1") && "".equals(key)) {
                    break;
                }
                if ("".equals(key)) {
                    key = "未知";
                }
                nameList.add(key);
                valueList.add(entry.getValue());
            }
        }
        map.put(ConstantStr.NAME, nameList);
        map.put(ConstantStr.VALUE, valueList);
        return map;
    }

    private Map nextProfileUtil(String crowd,String parentType, String type, String beginDate, String endDate, String isLogin,
        String flag) {

        Map<String, Object> map = new HashMap<>(16);
        long start = System.currentTimeMillis();

        String sexStr = null;
        String generationStr = null;
        String professionStr = null;
        String cityStr = null;
        String provinceStr = null;
        String incomeStr = null;
        String chronStr = null;
        String taboosStr = null;
        String tagsStr = null;
        String tagsSexStr=null;
        String taboosSexStr=null;
        String chronSexStr=null;

        String statesStr=null;
        String statesSexStr=null;

        String sign="0";


        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(14);
        //创建九个有返回值的任务       执行任务并获取Future对象
        Future sex = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.SEX, beginDate, endDate, isLogin,sign));
        Future generation =
            pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.GENERATION, beginDate, endDate, isLogin,sign));
        Future profession =
            pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.PROFESSION, beginDate, endDate, isLogin,sign));
        Future city = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.CITY, beginDate, endDate, isLogin,sign));
        Future province =
            pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.PROVINCE, beginDate, endDate, isLogin,sign));
        Future income = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.INCOME, beginDate, endDate, isLogin,sign));
        Future chron =
            pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.CHRONICILLNESS_NAME, beginDate, endDate, isLogin,sign));
        Future taboos =
            pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.TABOOS_NAME, beginDate, endDate, isLogin,sign));
        Future tags = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.TAGS_TAG, beginDate, endDate, isLogin,sign));

        Future states = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.STATES_NAME, beginDate, endDate, isLogin,sign));

        //解决兴趣、忌口、疾病占比总人数的问题  并去除标签不等于空的人数

        Future tagsSex = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.SEX, beginDate, endDate, isLogin,"interested_tags.tag"));
        Future taboosSex = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.SEX, beginDate, endDate, isLogin,"taboos.name"));
        Future chronSex = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.SEX, beginDate, endDate, isLogin,"chronicillness.name"));
        Future statesSex = pool.submit(new MyCallable(crowd,parentType, type, ProfileUtil.SEX, beginDate, endDate, isLogin,"states.name"));
        //从Future对象上获取任务的返回值
        try {
            sexStr = sex.get().toString();
            generationStr = generation.get().toString();
            professionStr = profession.get().toString();
            cityStr = city.get().toString();
            provinceStr = province.get().toString();
            incomeStr = income.get().toString();
            chronStr = chron.get().toString();
            taboosStr = taboos.get().toString();
            tagsStr = tags.get().toString();
            tagsSexStr=tagsSex.get().toString();
            taboosSexStr=taboosSex.get().toString();
            chronSexStr=chronSex.get().toString();

            statesStr=states.get().toString();
            statesSexStr=statesSex.get().toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        //关闭线程池
        pool.shutdown();

        long end = System.currentTimeMillis();

        System.out.println("二级标签九个请求时间：" + (end - start));
        map.put(ProfileUtil.SEX, converList(sexStr, type, flag));
        map.put(ProfileUtil.GENERATION, converList(generationStr, type, flag));
        map.put(ProfileUtil.PROFESSION, converList(professionStr, type, flag));
        map.put(ProfileUtil.CITY, converList(cityStr, type, flag));
        map.put(ProfileUtil.PROVINCE, converList(provinceStr, type, flag));
        map.put(ProfileUtil.INCOME, converList(incomeStr, type, flag));

        Map tmpChronSex=converList(chronSexStr,type,flag);
        Map map1=converList(chronStr, type, flag);
        map1.put("total",tmpChronSex.get("total"));
        map.put("chron",map1);

        Map tmpTaboosSex =converList(taboosSexStr,type,flag);
        Map map2=converList(taboosStr, type, flag);
        map2.put("total",tmpTaboosSex.get("total"));
        map.put("taboos", map2);

        Map tmpTagSex=converList(tagsSexStr,type,flag);
        Map map3=converList(tagsStr, type, flag);
        map3.put("total",tmpTagSex.get("total"));
        map.put("tags",map3);

        Map tmpStatesSex=converList(statesSexStr,type,flag);
        Map map4=converList(statesStr, type, flag);
        map4.put("total",tmpStatesSex.get("total"));
        map.put("states",map4);
        map.put(ConstantStr.NEXTTITLE, type);
        return map;
    }

    /**
     * 带有返回值的线程实现
     */
    class MyCallable implements Callable<Object> {
        private String parentType;
        private String type;
        private String target;
        private String str;
        private String startDate;
        private String endDate;
        private String isLogin;
        private String crowd;
        private String flag;

        MyCallable(String crowd,String parentType, String type, String target, String startDate, String endDate, String isLogin,String flag) {
            this.parentType = parentType;
            this.type = type;
            this.target = target;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isLogin = isLogin;
            this.crowd=crowd;
            this.flag=flag;

        }

        @Override public Object call() throws Exception {

            if (StringUtils.isBlank(parentType)){
                str=testEsCliectUtil.getRecommendCooks(crowd,target, ConstantStr.SIZE, startDate, endDate, isLogin,flag).toString();

            }else {
                str = testEsCliectUtil
                    .getRecommendCooks(crowd,parentType, type, target, ConstantStr.SIZE, startDate, endDate, isLogin,flag).toString();
            }

            return str;
        }
    }

    /**
     * 导出excel数据转换
     *
     * @param sexMap
     * @return
     */
    private List excelConverDateUtil(Map sexMap) {

        List list = new ArrayList();

        List<Map> sexList = (List)sexMap.get(ConstantStr.DATA);
        int total = (int)sexMap.get(ConstantStr.TOTAL);

        int index = 1;
        for (int i = 0; i < sexList.size(); i++) {
            String[] data = new String[4];

            data[0] = String.valueOf(index);
            Map target = sexList.get(i);

            int value = (int)target.get(ConstantStr.VALUE);

            data[1] = (String)target.get(ConstantStr.NAME);
            data[2] = String.valueOf(value);
            data[3] = BigDecimalUtil.deciMal(value * 100, total) + "%";

            list.add(data);
            index++;
        }
        return list;
    }
}
