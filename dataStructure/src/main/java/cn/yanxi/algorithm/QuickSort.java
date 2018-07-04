package cn.yanxi.algorithm;

/**
 * Created by lcyanxi on 2018/6/29.
 * 快速排序算法
 */
public class QuickSort {
    public static int quickSort(int arry[], int low, int hight) {
        //默认拿第一个元素作为标记值
        int key = arry[low];
        //从表的两端开始向表的中间交替扫描
        while (low < hight) {
            //如果最左端的值大于标记值，则下标往前移动一位
            while (low < hight && arry[hight] >= key){
                --hight;
            }
            //否则把最左的值放在第一个位置
            arry[low] = arry[hight];
            while (low < hight && arry[low] <= key){
                ++low;
            }
            arry[hight] = arry[low];
        }
        //把标记值放在low下标的位置
        arry[low]=key;
        return low;
    }
    public static void    QSort(int arry[],int low,int hight){
        //取长度大于1的数组
        if(low<hight){
            //一趟排序完返回标记值的位置
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
