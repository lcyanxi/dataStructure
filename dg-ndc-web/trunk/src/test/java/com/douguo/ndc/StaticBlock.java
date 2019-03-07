package com.douguo.ndc;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/23
 */
public class StaticBlock {

    static Block block=new Block();


    StaticBlock(){
        System.out.println("构造方法执行-------");
    }

    static {
        System.out.println("静态代码块执行******");
    }
    {
        System.out.println("常量代码块执行########");
    }

    public void  methodBlock(){
        System.out.println("常量方法执行");
    }


    public void test(){
        block.test();
    }

    public static void main(String args[]){
        System.out.println("main方法开始执行。。。。。。。。。。。。。。");
        StaticBlock staticBlock=new StaticBlock();
        staticBlock.methodBlock();

        StaticBlock staticBlock1=new StaticBlock();
        staticBlock1.methodBlock();
    }

    static  class Block{

        static {
            System.out.println("Block静态函数函数执行----------");
        }

        public Block() {
            System.out.println("Block构造函数执行----------");
        }
        public void test(){
            System.out.println("Block常量函数执行");
        }
    }
}
