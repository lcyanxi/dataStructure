package cn.yanxi.queue.queueArray;

import cn.yanxi.queue.Queue;
import cn.yanxi.queue.QueueEmptyException;

/**
 * Created by lcyanxi on 16-12-10.
 */
public class QueueArray implements Queue {

    //该队列用少用一个存储单元实现

    private static  final int LEN=10;//默认数组大小
    private  Object[] element;//数据元素组
    private  int  capacity;//数组的大小 （element.length）
    private  int rear;//队尾指针
    private int font;//队头指针

    public QueueArray() {
        this(LEN);
    }

    public QueueArray(int len) {
        this.capacity = len+1;
        element=new Object[capacity];
        rear=font=0;
    }

    public int getSize() {
        return (rear-font+capacity)%capacity;
    }

    public boolean isEmpty() {
        return rear==font;
    }

    public void enQueue(Object object) {
        if (getSize()>capacity-1){
          expandSpace();
        }
        element[rear]=object;
        rear=(rear+1)%capacity;

    }
    private void expandSpace(){
        Object[] objects=new Object[capacity*2];
        int i=font ;
        int j=0;
        while (i!=rear){
            objects[j++]=element[i];
            i=(i+1)%capacity;
        }
        element=objects;
        capacity=element.length;
        font=0;//重新设置队首和队尾指针
        rear=j;
    }

    public Object deQueue() throws QueueEmptyException {
        if (isEmpty()){
            throw new QueueEmptyException("对不起，该队列为空！！");
        }
        Object object=element[font];
        element[font]=null;
        font=(font+1)%capacity;
        return object;
    }

    public Object getFont() throws QueueEmptyException {
        if (isEmpty()){
            throw new QueueEmptyException("对不起，该队列为空！！");
        }
        return element[font];
    }
}
