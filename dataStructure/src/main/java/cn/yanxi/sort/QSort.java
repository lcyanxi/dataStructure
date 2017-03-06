package cn.yanxi.sort;


/**
 * Created by lcyanxi on 17-3-3.
 */
public class QSort {
    public static void qSort(int array[],int low,int high){
        if(low<high){
            int pivotloc= PartitionSort(array,low,high);
            qSort(array,low,pivotloc-1);
            qSort(array,pivotloc+1,high);
        }
    }
    public static int  PartitionSort(int array[],int low ,int high){
        int pivotKey=array[low];
        while (low<high){
            while (low<high && array[high]>=pivotKey)//将比关键字pivotKey小的移到右边
                high--;
            array[low] =array[high];
            while (low<high && array[low]<=pivotKey)//将比关键字pivotKey大的移到左边
                low ++;
            array[high]=array[low];
        }
        array[low]=pivotKey;//把关键字放到最后空出来的那个地，并返回下标值
        return low;
    }
}
