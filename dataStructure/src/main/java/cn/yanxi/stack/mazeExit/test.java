package cn.yanxi.stack.mazeExit;

import cn.yanxi.stack.StackEmptyException;

import java.util.Scanner;

/**
 * Created by lcyanxi on 16-12-11.
 */
public class test {

    /*测试数据
    10
    10
    1 1 1 1 1 1 1 1 1 1
    1 0 0 1 1 1 0 0 1 1
    1 0 0 1 1 0 0 1 1 1
    1 0 0 0 0 0 0 1 0 1
    1 0 0 0 0 1 1 0 0 1
    1 0 0 1 1 1 0 0 0 1
    1 0 0 0 0 1 0 1 0 1
    1 0 1 1 0 0 0 1 0 1
    1 1 0 0 0 0 1 0 0 1
    1 1 1 1 1 1 1 1 1 1
    8
    8
    1
    7
     */

    private  static final MazeExit maze=new MazeExit();
    private static  Scanner scanner;

    public static void main(String args[]){
        System.out.println("请初始化一个二维数组的模拟迷宫：");
        try{
            initData();
        }catch (StackEmptyException e){
            System.out.println("我找不到！！！");
        }
    }

    private static void initData() throws StackEmptyException{
        System.out.println("请输入你要初始化的二维数组的行和列：");
        scanner=new Scanner(System.in);
        int row=scanner.nextInt();
        int los=scanner.nextInt();
        char[][] objects=new char[row][los];
        for (int i=0;i<row;i++){
            for (int j=0;j<los;j++){
                objects[i][j]=(char) scanner.nextInt();
            }
        }
        System.out.println("请指定一个起点坐标值：");
        int sx=scanner.nextInt();
        int sy=scanner.nextInt();
        System.out.println("请指定一个终点坐标值：");
        int ex=scanner.nextInt();
        int ey=scanner.nextInt();
        maze.mazeExit(objects,sx,sy,ex,ey);
    }

}
