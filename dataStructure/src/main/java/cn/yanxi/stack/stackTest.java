package cn.yanxi.stack;

import cn.yanxi.stack.stackArray.StackArray;
import cn.yanxi.stack.stackLinked.StackLinked;
import cn.yanxi.stack.stackLinked.StackLinkedByTop;

import java.util.Scanner;

/**
 * Created by lcyanxi on 16-12-8.
 */
public class stackTest {
    //private final static Stack stack=new StackArray();//栈的线性存储实现（基于数组）
    //private  static  Stack stack=new StackLinked();//栈的链式存储（基于链表）
    private  final  static Stack stack=new StackLinkedByTop();
    private static  Scanner scanner;

    public static void main(String args[]){
        scanner=new Scanner(System.in);

        while (true){
            try {
                showMenu();
                int i=scanner.nextInt();
                switch (i){
                    case 0:
                        initData();
                        break;
                    case 1:
                        isEmpty();
                        break;
                    case 2:
                        getSize();
                        break;
                    case 3:
                        pop();
                        break;
                    case 4:
                        getTop();
                        break;
                    case 5:
                        popAll();
                        break;
                    default:
                        System.out.println("对不起，你输入的指令有问题！！");
                        break;
                }
            }catch (Exception e ){
                System.out.println("对不起，你输入的数据有问题，请重新输入！！！");
                e.printStackTrace();
            }
        }

    }

    public static  void showMenu(){
        System.out.println(
                "************JAVA版本的栈***********************\n" +
                        "**        您可以根据如下提示进行操作：\n" +
                        "**   按数字“0”入栈\n" +
                        "**   按数字“1”判断栈是否为空\n" +
                        "**   按数字“2”查看栈中元素个数\n"+
                        "**   按数字“3”出栈\n" +
                        "**   按数字“4”获得栈顶元素\n" +
                        "**   按数字“5”全部出栈\n" +
                        "***************************************************"
        );
    }

    public static  void initData() throws StackEmptyException{
        System.out.println("请输入你要初始化元素的个数：");
        scanner=new Scanner(System.in);
        int i=scanner.nextInt();
        System.out.println("请输入你要初始化的数据：");
        for (int j=0;j<i;j++)
            stack.push(scanner.nextInt());
         showData();
        }

    public static void isEmpty(){
         String message=stack.isEmpty()?"空":"非空";
         System.out.println("当前栈为："+message);
        }

    public static void getSize(){
        System.out.println("当前栈元素为："+stack.getSize());
    }

    public static void popAll() throws StackEmptyException{
        System.out.print("出栈序列：");
        while (!stack.isEmpty())
            System.out.print(" "+stack.pop());
        System.out.println("\n");
    }

    public static void pop() throws StackEmptyException{
            System.out.println("出栈元素为："+stack.pop());
            showData();
    }

    public static void getTop() throws StackEmptyException{
        System.out.println("栈顶元素为："+stack.getTop());
    }

    public static void showData(){
        Object[] objects=stack.showData();
        System.out.print("栈中元素为：");
        for (int i=0;i<objects.length;i++){
            System.out.print(" "+objects[i]);
        }
        System.out.println("\n");
    }


}



