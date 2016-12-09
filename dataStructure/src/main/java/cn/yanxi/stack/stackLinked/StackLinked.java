package cn.yanxi.stack.stackLinked;

import cn.yanxi.stack.Stack;
import cn.yanxi.stack.StackEmptyException;

/**
 * Created by lcyanxi on 16-12-9.
 */
public class StackLinked implements Stack {
    private int size;//栈的大小
    private SLNode top;//链表首节点引用

    public StackLinked() {
        System.out.println("初始化内存！！！");
        size=0;
        top=null;
    }


    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public void push(Object object) {
        SLNode p=new SLNode(object,top);
        top=p;
        size++;
        return;

    }

    public Object pop() throws StackEmptyException {
        if (size<1){
            throw  new StackEmptyException("该栈为空！！！");
        }
        Object object=top.getData();
        top=top.getNext();
        size--;
        return object;
    }

    public Object getTop() throws StackEmptyException {
        if (size<1){
            throw  new StackEmptyException("该栈为空！！！");
        }
        Object object=null;
        if (top.getNext()!=null){
          object= top.getData();
        }
        return object;
    }

    public Object[] showData() {
        Object[] objects=null;
        if (size>1){
           objects=new Object[size];
        }
        for (int i=0;i<size;i++){
            objects[i]=top.getData();
            top=top.getNext();
        }


        return objects;
    }
}
