package cn.yanxi.search;

import org.junit.Test;

/**
 * Created by lcyanxi on 17-3-1.
 */
public class BinSearchTest {
    @Test
    public void binSearchTest(){
        int [] s={1,3,5,6,7,9,11,45};
        int key=11;
       System.out.println(BinSearch.binSearch(s,0,s.length,key));
    }

    @Test
    public void test(){
        int num=32;
        int i=0;
        for(int j=0;j<100;j++){
            i=i++;
        }
        System.out.println(i);
        System.out.print("value is:"+((i>5) ? 10.9:9));
        System.out.println(num >> 32);
        System.out.println(9/2);
    }
}
