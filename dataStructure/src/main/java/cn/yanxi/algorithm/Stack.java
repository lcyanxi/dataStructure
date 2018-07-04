package cn.yanxi.algorithm;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Created by lichang on 2018/7/4
 */
public class Stack {
    private int size;
    private Object[] item;
    private int top;

    /**
     *     初始化参数，构造一个容量为10的栈
     */
    public Stack() {
        item = new Object[10];
        size = 10;
        top = -1;
    }

    public Stack(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("栈的初始化容量大小不能小于0");
        }
        this.item = new Object[size];
        this.size = size;
        this.top = -1;
    }

    /**
     * 入栈
     * @param data
     */
    public void push(Object data) {
        //判断是否扩容
        if (top + 1 >= size ) {
            groupCapacity();
        }
        item[++top] = data;
    }

    /**
     * 出栈
     * @return
     */
    public Object pop() {
        if (top == -1) {
            throw new EmptyStackException();
        }
        Object object = item[top];
        item[top] = null;
        top--;
        return object;
    }

    /**
     * 获取栈顶元素
     * @return
     */
    public Object getTop() {
        if (top == -1) {
            throw new EmptyStackException();
        }
        return item[top];
    }

    public void groupCapacity() {
        int newSize = 0;
        //判断数组按一倍库容后是否超过int数组的最大长度
        if ((size << 1) - Integer.MAX_VALUE > 0) {
            newSize = Integer.MAX_VALUE;
        } else {
            //将原数组按一倍扩容
            newSize = (size << 1);
        }
        this.size = newSize;
        //拷贝原数组到扩容数组里，指定数组长度
        this.item = Arrays.copyOf(item, newSize);

    }

    public static void main(String args[]) {
        Stack stack = new Stack(5);
        stack.push(33);
        stack.push(11);
        stack.push(33);
        stack.push(44);
        stack.push("aaaaa");
        System.out.println(stack.getTop());
        stack.push("bbbbb");
        System.out.println(stack.getTop() + ":" + stack.size);
        stack.pop();
        stack.pop();
        System.out.println(stack.getTop());
    }

}
