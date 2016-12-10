package cn.yanxi.queue;

import cn.yanxi.queue.Queue;
import cn.yanxi.queue.queueArray.QueueArray;
import cn.yanxi.queue.queueLinked.QueueLinked;
import org.junit.Test;

/**
 * Created by lcyanxi on 16-12-10.
 */
public class test {
    @Test
    public void test(){

       // Queue queue=new QueueArray();//数组实现
        Queue queue=new QueueLinked();//链表实现


        System.out.println("当前队列为："+queue.isEmpty());
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        System.out.println("当前队列长度："+queue.getSize());
        queue.deQueue();
        queue.enQueue(4);
        queue.enQueue(5);
        queue.enQueue(6);
        System.out.println("当前队列长度："+queue.getSize());
        System.out.print("出队：");
        while (queue.getSize()!=0){
            System.out.print(queue.deQueue());
        }

    }

}
