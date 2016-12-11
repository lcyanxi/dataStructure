package cn.yanxi.stack.mazeExit;

/**
 * Created by lcyanxi on 16-12-11.
 */
public class Cell {
    int x=0;//单元行
    int y=0;//单元列
    boolean visited;//是否被访问
    char c=' ';//(1)表示墙 （0）通道 （×）路径

    public Cell(int x, int y, boolean visited, char c) {
        this.x = x;
        this.y = y;
        this.visited = visited;
        this.c = c;
    }
}
