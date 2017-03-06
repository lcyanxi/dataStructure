package cn.yanxi.sort;

/**
 * Created by lcyanxi on 17-3-2.
 */
public class ShellSort {
    /**
     *
     * @param array
     * @param keys
     *    步长值为质数
     *   希尔排序最后一个步长值增量必须为1
     *   希尔排序是不稳地的，列如两个相同元素比较可能他们的位置要发生变化
     */
    public static void shellSort(int array [],int keys []){
        for (int i=0;i<keys.length;i++){
          shellInsert(array,keys[i]);
        }
    }
    private static void shellInsert(int array [],int key){
        for(int i=0+ key;i<array.length;i++){
            if(array[i]-array[i-key]<0){//和它上一个间距的元素比较
                int temp=array[i];     //将要移动的元素暂时存在一个临时变量中
                int j=i-key;           //将游标指到上一个元素位置
                for (;j>=0 && temp-array[j]<0;j=j-key){ //依次上一个流程
                    array[j+key]=array[j];  //交换位置
                }
                array[j+key]=temp;//将这个值插入到它适合的位置
            }
        }
    }
    private static void shellInsert2(int array [],int key){
        for(int i=key;i<array.length;i++){
            if (array[i]-array[i-key]<0){
                int temp=array[i];
                int j=i-key;
                do{
                  array[j+key]=array[j];
                  j=j-key;
                }while (j>0 && temp<array[j]);
                array[j+key]=temp;
            }
        }
    }



}
