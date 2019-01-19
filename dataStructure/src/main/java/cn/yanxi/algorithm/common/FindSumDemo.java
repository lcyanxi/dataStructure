package cn.yanxi.algorithm.common;

/**
 * Created by lcyanxi on 2019/1/13
 * 给出一个排序好的数组和一个数，求数组中连续元素的和等于所给数的子数组
 */

public class FindSumDemo {
    public static void main(String args[]){
        int[] num = {1,2,2,3,4,5,6,7,8,9};
        int sum = 7;
        findSum(num,sum);
    }

    public static void findSum(int arr[],int sum){
        for (int i=0;i<arr.length;i++){
            int tmp=arr[i];
            String str=tmp+"";
            if (tmp>7){
                break;
            }
            if (tmp==7){
                System.out.print(tmp);
            }

            for (int j=i+1;j<arr.length;j++){
                tmp=tmp+arr[j];
                if (tmp<7){
                    tmp=tmp+arr[j];
                    str=str+arr[j]+"";
                }
                if (tmp==7){
                    System.out.println(str+arr[j]);
                }
                else {
                    break;
                }
            }
        }
    }
}
