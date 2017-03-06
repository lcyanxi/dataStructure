package cn.yanxi.sort;

/**
 * Created by lcyanxi on 17-3-2.
 */
public class MergeSort {
    /**
     *   归并排序
     * @param array
     * @param start
     * @param end
     *
     */
    public static void  mergeSort(int [] array,int start,int end){
        if (start<end){
            int mid=(start+end)/2;
            mergeSort(array,start,mid);//不断的划分序列，让最后每个序列只有两个元素
            mergeSort(array,mid+1,end);
            merger(array,start,mid ,mid+1,end);
        }

    }

    public static void  merger(int [] array,int start1,int end1  ,int start2,int end2) {
        int[] temp = new int[end2 - start1 +1];
        int i = start1;
        int j = start2;
        int k = 0;
        while (i <= end1 && j <=end2) {
            if (array[i] > array[j])
                temp[k++] = array[j++];
            else temp[k++] = array[i++];
        }
        while (i<=end1){
            temp[k++] = array[i++];
        }
        while (j<=end2){
            temp[k++]= array[j++];
        }
        k=start1;
        for(int elment:temp){
            array[k++]=elment;
        }

    }

}
