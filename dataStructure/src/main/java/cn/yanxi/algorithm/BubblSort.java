package cn.yanxi.algorithm;

/**
 * Created by lichang on 2018/7/2
 * 冒泡排序
 */
public class BubblSort {

    public static void bubblSort(int arr[]){
        // 这里for循环表示总共需要比较多少轮
        for(int i=1;i<arr.length;i++){
            // j的范围很关键，这个范围是在逐步缩小的,因为每轮比较都会将最大的放在右边
            for (int j=0;j<arr.length-i;j++){
                if (arr[j]>arr[j+1]){
                    int tmp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=tmp;
                }
            }
        }
    }

    public static void main(String args[]){
        int[] arr={34,23,15,45,36,65,21,13,10};
        bubblSort(arr);
        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
