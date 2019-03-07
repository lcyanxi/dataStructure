package com.douguo.ndc;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichang on 2018/8/9
 */
public class ThreadTest {
    public static void main(String args[]) {
        MyThread myThread = new MyThread();
        myThread.start();
        System.out.println("--------------");
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
        System.out.println("-------------");

        //使用Executor 创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Runnable syncRunnable = new Runnable() {
            @Override public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(" ExecutorService:" + i);
                }
            }
        };
        executorService.execute(syncRunnable);

        System.out.println("-------------");
    }
}

/**
 * 继承Thread类
 */
class MyThread extends Thread {
    @Override public void run() {
        for (int i = 0; i <= 100; i++) {
            System.out.println("MyThread:" + i);
        }
    }
}

/**
 * 实现Runnable接口
 */
class MyRunnable implements Runnable {
    @Override public void run() {
        for (int i = 0; i <= 100; i++) {
            System.out.println("MyRunnable:" + i);
        }
    }
}
