package cn.yanxi.algorithm;

import java.util.Arrays;

/**
 * Created by lichang on 2018/7/4
 * 队列
 */
public class Queue {
    private Object[] item;
    //队头指针
    private int front;
    //队尾指针
    private int rear;
    //队数量
    private int size;

    public Queue() {
        this.item = new Object[10];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public Queue(int capacity) {
        this.item = new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    /**
     * 入队
     * @param data
     */
    public void insert(Object data) {
        if (size == item.length) {
            expendCapacity();
        }
        if (rear == item.length) {
            rear = 0;
        }
        item[rear] = data;
        rear++;
        size++;

    }

    /**
     * 扩容数组
     */
    private void expendCapacity() {
        int capacity = size;
        if ((capacity << 1) - Integer.MAX_VALUE > 0) {
            capacity = Integer.MAX_VALUE;
        } else {
            capacity = (size << 1);
        }
        item = Arrays.copyOf(item, capacity);
    }

    /**
     * 出栈
     * @return
     */
    public Object remove() {
        Object object=null;
        if (size != 0) {
            object=item[front];
            item[front] = null;
            front++;
            if (front == item.length) {
                front = 0;
            }
            size--;
        }
        return object;
    }

    /**
     * 获取队头数据
     * @return
     */
    public Object getFront() {
        return item[front];
    }

    /**
     * 获取队列大小
     * @return
     */
    public int getSize() {
        return size;
    }

    public static void main(String args[]) {
        Queue queue = new Queue(4);
        queue.insert(33);
        queue.insert(44);
        queue.insert(33);
        queue.insert(44);
        System.out.println(queue.getSize());
        queue.insert(55);
        System.out.println(queue.getSize());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.getFront());

    }
}
