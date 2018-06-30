package cn.yanxi.algorithm;

/**
 * Created by lcyanxi on 2018/6/29.
 * 快速排序算法
 */
public class QuickSort {
    public static int quickSort(int arry[], int low, int hight) {
        int key = arry[low];
        while (low < hight) {
            while (low < hight && arry[hight] >= key) --hight;
            arry[low] = arry[hight];
            while (low < hight && arry[low] <= key) ++low;
            arry[hight] = arry[low];
        }
        arry[low]=key;
        return low;
    }
    public static void    QSort(int arry[],int low,int hight){
        if(low<hight){
            int pivotkey=quickSort(arry,low,hight);
            QSort(arry,low,pivotkey-1);
            QSort(arry,pivotkey+1,hight);
        }
    }

    public static void main(String args[]){
        int[] arry=new int[]{32,12,45,25,78,62,11,36,22};
        QSort(arry,0,arry.length-1);
        for (int i=0;i<arry.length;i++){
            System.out.print(arry[i]+",");
        }

    }
}
