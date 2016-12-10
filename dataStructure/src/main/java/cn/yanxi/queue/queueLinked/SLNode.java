package cn.yanxi.queue.queueLinked;

/**
 * Created by lcyanxi on 16-12-10.
 */
public class SLNode {
    private Object data;
    private SLNode next;

    public SLNode() {
        this(null,null);
    }

    public SLNode(Object data, SLNode next) {
        this.data = data;
        this.next = next;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SLNode getNext() {
        return next;
    }

    public void setNext(SLNode next) {
        this.next = next;
    }
}
