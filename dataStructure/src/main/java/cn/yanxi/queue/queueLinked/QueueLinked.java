package cn.yanxi.queue.queueLinked;

import cn.yanxi.queue.Queue;
import cn.yanxi.queue.QueueEmptyException;

/**
 * Created by lcyanxi on 16-12-10.
 */
public class QueueLinked implements Queue {

    private int size;
    private SLNode font;
    private SLNode rear;

    public QueueLinked() {
        size=0;
        font=new SLNode();
        rear=font;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public void enQueue(Object object) {
        SLNode p=new SLNode(object,null);
        rear.setNext(p);
        rear=p;
        size++;

    }

    public Object deQueue() throws QueueEmptyException {
        if (size<1){
            throw new QueueEmptyException("对不起，该队列为空！！");
        }
        Object object=font.getNext().getData();
        font.setNext(font.getNext().getNext());
        size--;
        if (size<1) font=rear;

        return object;
    }

    public Object getFont() throws QueueEmptyException {
        if (size<1){
            throw new QueueEmptyException("对不起，该队列为空！！");
        }
        return font.getNext().getData();
    }
}






