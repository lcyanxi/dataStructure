package cn.yanxi.algorithm;

/**
 * Created by lcyanxi on 2018/6/30.
 * 希尔排序   增量值计算length/3+1
 */
public class ShellSort {
    public static void shellSort(int arr[]){
        int increatement=arr.length;
        while (increatement>1){  //最后一次为步长为1的全排
            increatement=increatement/3+1;//和直接插入排序类似，只是比较的不是前一个元素了，比较的是前第increatement的对比
            for (int i=increatement;i<arr.length;i++){
                int key=arr[i];
                int j;
                for (j=i-increatement;j>=0&&key<arr[j];j=j-increatement){
                    arr[j+increatement]=arr[j];
                }
                arr[j+increatement]=key;
            }
        }
    }

    public static void main(String args[]) {
        int[] arr = new int[]{11, 21, 5, 15, 34, 23, 14, 31, 3};
        shellSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
    }
}
