package cn.yanxi.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcyanxi on 2019/3/14
 */

public class Solution {
    public static void main(String[] args) {
        String str="bbbbb";
        System.out.println(solution(str));
    }

    public static int solution(String str){
        int longest=0;
        char tmp[]=str.toCharArray();
        List list=new ArrayList();
        for (int j=0;j<tmp.length;j++){
            for (int i=j;i<tmp.length;i++){
                if (list.contains(tmp[i])){
                    break;
                }else {
                    list.add(tmp[i]);
                }
            }
            int result=list.size();
            longest=longest>result?longest:result;
            if (longest>=(str.length()-j+1)) break;
            list.clear();
        }

        return longest;
    }
}
