package cn.yanxi.algorithm;

/**
 * Created by lichang on 2018/7/3
 * 归并排序
 */
public class MergeSort {

    public static void sort(int arr[]) {
        int[] tmp = new int[arr.length];
        sort(arr, 0, arr.length - 1, tmp);

    }

    public static void sort(int arr[], int left, int right, int tmp[]) {
        if (left < right) {
            int mid = (left + right) / 2;
            //左边归并排序，使得左子序列有序
            sort(arr, left, mid, tmp);
            //右边归并排序，使得右子序列有序
            sort(arr, mid + 1, right, tmp);
            //将两个有序子数组合并操作
            mergeSort(arr, left, mid, right, tmp);
        }
    }

    public static void mergeSort(int arr[], int left, int mid, int right, int tmp[]) {
        int i = left;
        int j = mid + 1;
        int index = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tmp[index++] = arr[i++];
            } else {
                tmp[index++] = arr[j++];
            }
        }
        //把左边的剩余数据填入临时数组里
        while (i <= mid) {
            tmp[index++] = arr[i++];
        }
        //把右边的剩余数据填入临时数组里
        while (j <= right) {
            tmp[index++] = arr[j++];
        }
        //把临时数组的数据拷贝到原数组里
        index = 0;
        while (left <= right) {
            arr[left++] = tmp[index++];
        }
    }


    public static void main(String args[]) {
        int[] arr = {34, 23, 15, 45, 36, 65, 21, 13, 10};
        sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
