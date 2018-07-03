package cn.yanxi.algorithm;

/**
 * Created by lichang on 2018/7/2
 */
public class ChooseSort {

    public static void chooseSort(int arr[]){
        // 总共要经过N-1轮比较
        for (int i=0;i<arr.length-1;i++){
            int tmp=i;
            // 每轮需要比较的次数
            for (int  j=i+1;j<arr.length;j++){
                if (arr[j]<arr[tmp]){
                    // 记录目前能找到的最小值元素的下标
                    tmp=j;
                }
            }
            // 将找到的最小值和i位置所在的值进行交换
            if (tmp!=i){
                int key=arr[i];
                arr[i]=arr[tmp];
                arr[tmp]=key;
            }
        }
    }

    public static void main(String args[]){
        int[] arr={34,23,15,45,36,65,21,13,10};
        chooseSort(arr);
        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
