package cn.yanxi.stack.stackLinked;

import cn.yanxi.stack.Stack;
import cn.yanxi.stack.StackEmptyException;

/**
 * Created by lcyanxi on 16-12-9.
 */
public class StackLinkedByTop implements Stack{
    private int size;
    private SLNode top;

    public StackLinkedByTop() {
        size=0;
        top=new SLNode();
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public void push(Object object) {
        SLNode p=top;
        SLNode q=new SLNode(object,top.getNext());
        p.setNext(q);
        size++;
        return;
    }

    public Object pop() throws StackEmptyException {
        if (size<1){
            throw  new StackEmptyException("该栈为空！！！");
        }
           SLNode p=top;
           Object object=top.getNext().getData();
           p.setNext(top.getNext().getNext());
           size--;
        return null;
    }

    public Object getTop() throws StackEmptyException {
        if (size<1){
            throw  new StackEmptyException("该栈为空！！！");
        }
        System.out.println("top"+top);
        return top.getNext().getData();
    }

    public Object[] showData() {
        SLNode p=top;
        System.out.println("size"+size);
        Object[] objects=new Object[size];
        for (int i=0;i<size;i++){
            objects[i]=p.getNext().getData();
            top.setNext(p.getNext().getNext());
        }
        return objects;
    }
}
