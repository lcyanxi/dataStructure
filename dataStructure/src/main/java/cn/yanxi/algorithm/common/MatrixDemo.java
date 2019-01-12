package cn.yanxi.algorithm.common;

/**
 * Created by lcyanxi on 2019/1/12
 *   题目：输入一个矩阵，按顺时针由外向内打印每一个数据
 *   1   2   3   4
 *   5   6   7   8
 *   9  10  11  12
 *   13 14  15  16
 *
 *   输出 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
 */

public class MatrixDemo {
    public static void main(String args[]){

        int arr[][]=new int[100][100];
        int n=4;
        int num=1;
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                arr[i][j]=num++;
            }
        }
        matrix(arr,0,3);
    }

    /**
     * 思路：以行为基础，遍历行。以列为基础，遍历列，以行为基础，倒序遍历行，以列为基础，倒序遍历列。递归进入下一层
     * @param arr  数据源
     * @param start 开始的行
     * @param end 开始的列
     */
    public static void matrix(int arr[][],int start,int end){

        if (start>end) return;

        for (int i=start;i<=end;i++){
            System.out.print(arr[start][i]);
        }
        for (int i=start+1;i<=end;i++){
            System.out.print(arr[i][end]);
        }
        for (int i=end-1;i>=start;i--){
            System.out.print(arr[end][i]);
        }
        for (int i=end-1;i>start;i--){
            System.out.print(arr[i][start]);
        }

        matrix(arr,start+1,end-1);

    }
}














