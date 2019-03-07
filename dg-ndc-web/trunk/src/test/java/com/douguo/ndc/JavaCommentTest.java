package com.douguo.ndc;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lichang on 2018/8/9
 */
public class JavaCommentTest {

    @Test public void testEques() {
        String a = new String("ab");
        // a 为一个引用
        String b = new String("ab"); // b为另一个引用,对象的内容一样
        String aa = "ab"; // 放在常量池中
        String bb = "ab"; // 从常量池中查找
        if (aa == bb) // true String为对象 但是String类已经重写equals方法了 所以比较的事内容
            System.out.println("aa==bb");
        if (a == b) // false，非同一对象  比较内存地址
            System.out.println("a==b");
        if (a.equals(b)) // true  比较内容
            System.out.println("aEQb");
        if (42 == 42.0) {// true
            System.out.println("true");
        }
    }

    @Test
    public  void testMap(){
        Map map=new HashMap(16);
        map.put("",null);
        map.put("",null);
        map.put("","");
        map.put("","");
        map.put("","");
        map.put("","");
        System.out.println(map.size());

    }

}



