package com.douguo.ndc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.caipusearch.util.SortByLengthComparator;
import com.douguo.ndc.util.DateUtil;
import com.douguo.ndc.util.JsonUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lichang on 2018/8/7
 */
public class MyUtilTest {

    final int a = 0;

    @Test public void test() {
        String str =
            "{\"list_tag\":[{\"\":10744183},{\"90后\":1345037},{\"80后\":780392},{\"85后\":517343},{\"70后\":236141},{\"95后\":173199},{\"00后\":145318},{\"60后\":76600}],\"list_tag_sub\":[{\"\":[{\"女\":8542109},{\"男\":2199961},{\"\":2113}]},{\"90后\":[{\"女\":1111556},{\"男\":233481}]},{\"80后\":[{\"女\":607785},{\"男\":172607}]},{\"85后\":[{\"女\":417395},{\"男\":99948}]},{\"70后\":[{\"女\":190604},{\"男\":45537}]},{\"95后\":[{\"女\":128067},{\"男\":45132}]},{\"00后\":[{\"女\":106805},{\"男\":38513}]},{\"60后\":[{\"女\":61648},{\"男\":14952}]}]}";

        JSONObject jsonConfig = JsonUtil.parseStrToJsonObj(str);
        // List<Map> tempList = (List)jsonConfig.get("list_tag");
        List<Map> tmp = (List<Map>)jsonConfig.get("list_tag_sub");
        List restult = null;
        for (Map map : tmp) {
            if (map.containsKey("80后")) {
                restult = (List)map.get("80后");
                break;
            }

        }
        System.out.println(restult);
    }

    @Test public void myTest() {

        JSONObject jsonObject = new JSONObject();
        LinkedHashSet list = new LinkedHashSet();
        list.add("aaaa");
        list.add("bbb");
        jsonObject.put("data", list);

        JSONObject jsonObject2 = new JSONObject();
        LinkedHashSet list2 = new LinkedHashSet();
        list2.add("1111");
        list2.add("2222");
        list2.add("bbb");
        jsonObject2.put("data", list2);

        LinkedHashSet list0 = (LinkedHashSet)jsonObject.get("data");
        LinkedHashSet list1 = (LinkedHashSet)jsonObject2.get("data");

        for (Object o : list0) {
            list1.add(o);
        }

        jsonObject.put("data", list1);

        System.out.println(jsonObject);

    }

    @Test public void testStatic() {
        BigDecimal bd = new BigDecimal(23.5);
        BigDecimal curNum = new BigDecimal((bd.doubleValue() / 4));
        System.out.println(curNum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%");
    }

    public double deciMal(int top, int below) {
        double result = new BigDecimal((float)top / below).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    @Test public void Test1() {
        float a=4574747.01541477f;
        double a2=11111845475.1;
        System.out.println(a);
        System.out.println(a2);

        double b1 = 0.06 + 0.01;
        float b2 = (float)(0.06 + 0.01);
        System.out.println(0.06 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(303.1 / 1000);
        System.out.println(b1);
        System.out.println(b2);

    }

}
