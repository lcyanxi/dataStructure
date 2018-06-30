package cn.yanxi.algorithm;

/**
 * Created by lcyanxi on 2018/6/30.
 * 直接插入排序
 */
public class StraightInsertSort {

    public static void straightInsertSort(int arr[]) {
        //从下标为1开始比较，直到数组的末尾
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                int key = arr[i];
                arr[i] = arr[i - 1];
                int j = i - 0;
                for (; j >= 0 && key < arr[j]; --j) {
                    arr[j + 1] = arr[j];
                }
                 arr[j + 1] = key;
            }
        }
    }

    public static void insertSort(int[] arr) {
        //从下标为1开始比较，直到数组的末尾
        for (int i = 1; i < arr.length; i++) {
            int j;
            //将要比较的元素，拿出待比较过后再插入数组
            int tmp = arr[i];
            //一次与前一元素比较，如果前一元素比要插入的元素大，则互换位置
            for (j = i - 1; j >= 0 && arr[j] > tmp; j--) {
                arr[j + 1] = arr[j];
            }
            //将比较的元素插入
            arr[j + 1] = tmp;
        }
    }

    public static void main(String args[]) {
        int[] arr = new int[]{11, 21, 5, 15, 34, 23, 14, 31, 3};
        straightInsertSort(arr);
        //insertSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
    }
}
