package cn.yanxi.stack.mazeExit;

import cn.yanxi.stack.Stack;
import cn.yanxi.stack.StackEmptyException;
import cn.yanxi.stack.stackArray.StackArray;
import cn.yanxi.stack.stackLinked.StackLinked;

/**
 * Created by lcyanxi on 16-12-11.
 */
public class MazeExit {


    public void mazeExit(char[][] maze,int sx,int sy,int ex,int ey) throws StackEmptyException{

        Cell[][] cells=createMaze(maze);//创建迷宫
        printMaze(cells);//打印迷宫
        Stack stack=new StackArray();//用堆栈实现
        Cell startCell=cells[sx][sy];//设置起点
        Cell endCell=cells[ex][ey];//设置终点
        stack.push(startCell);//起点入栈
        startCell.visited=true;//标记起点被访问过

        while (!stack.isEmpty()){
            Cell top=(Cell) stack.getTop();//获得栈顶元素

            if (top==endCell){//路径找到
                while (!stack.isEmpty()){//全部出栈
                    Cell object=(Cell) stack.pop();
                    object.c='*';
                    while (!stack.isEmpty()&&!isAdjoinCell((Cell) stack.getTop(),object)){
                        stack.pop();
                    }

                }
               System.out.println("找到起点到终点的路径如下：");
                printMaze(cells);
                return;


            }else {
                int x=top.x;
                int y=top.y;
                int count=0;//按照上、左、下、右顺序判断

                if (isValiadWay(cells[x-1][y])){//上
                    stack.push(cells[x-1][y]);
                    cells[x-1][y].visited=true;
                    count++;
                }
                if (isValiadWay(cells[x][y-1])){//左
                    stack.push(cells[x][y-1]);
                    cells[x][y-1].visited=true;
                    count++;
                }
                if (isValiadWay(cells[x+1][y])){//下
                    stack.push(cells[x+1][y]);
                    cells[x+1][y].visited=true;
                    count++;
                }
                if (isValiadWay(cells[x][y+1])){//右
                    stack.push(cells[x][y+1]);
                    cells[x][y+1].visited=true;
                    count++;
                }
                if (count==0){  //说明该点为死点 出栈
                    stack.pop();
                }
            }//endif
        }//endwhile
        System.out.println("对不起，没有从起点到终点的路径！！");

    }

    /**
     *
     * @param cell1
     * @param cell2  只用两个节点相邻才为有效
     * @return
     */
    private boolean isAdjoinCell(Cell cell1,Cell cell2){

        if (cell1.x==cell2.x&&Math.abs(cell1.y-cell2.y)<2) //当同一行的时候比较列
            return true;

        if (cell1.y==cell2.y && Math.abs(cell1.x-cell2.x)<2)//当同一列的时候比较同一行
            return true;

        return false;
    }

    /**
     *
     * @param cell  判断该点是否有效
     * @return
     */
    private boolean isValiadWay(Cell cell){
        return cell.c=='0'&&!cell.visited;//有效路径应为（0）并且还应该为没有被访问
    }


    /**
     *
     * @param cells 打印迷宫
     */
    private void printMaze(Cell[][] cells){
        for (int i=0;i<cells.length;i++){
            for (int j=0;j<cells[i].length;j++){
                System.out.print(cells[i][j].c);
            }
            System.out.println();
        }
    }

    /**
     *
     * @param maze
     * @return    创建一个模拟的迷宫
     */
    private Cell[][] createMaze(char[][] maze){
        Cell[][] cells=new Cell[maze.length][];
        for (int i=0;i<maze.length;i++){
            char[] row =maze[i];
            cells[i]=new Cell[row.length];
            for (int j=0;j<row.length;j++){
                cells[i][j]=new Cell(i,j,false,maze[i][j]);
            }
        }
        return cells;

    }


}
