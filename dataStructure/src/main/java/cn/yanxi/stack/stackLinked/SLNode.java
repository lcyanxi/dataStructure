package cn.yanxi.stack.stackLinked;

/**
 * Created by lcyanxi on 16-12-9.
 */
public class SLNode {
    private Object data;//数据域
    private SLNode next;//指针域

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
