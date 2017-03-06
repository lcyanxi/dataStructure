package cn.yanxi.sort;

/**
 * Created by lcyanxi on 17-3-2.
 */
public class Test {

    @org.junit.Test
    public void mergeSortTest(){
        int a []={1,5,8,7,3,6,9,4,2,5,4,0,5};
        MergeSort.mergeSort(a,0,a.length-1);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]);
        }
    }

    @org.junit.Test
    public void shellSortTest(){
        int a []={1,5,8,7,3,6,9,4,2,5,4,0,5};
        int key []={5,3,1};
        ShellSort.shellSort(a,key);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]);
        }
    }
    @org.junit.Test
    public void qSortTest(){
        int a []={1,5,8,7,3,6,9,4,2,5,4,0,5};
        QSort.qSort(a,0,a.length-1);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]);
        }
    }

}
