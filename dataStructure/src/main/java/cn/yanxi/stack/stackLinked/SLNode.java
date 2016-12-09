package cn.yanxi.stack.stackLinked;

/**
 * Created by lcyanxi on 16-12-9.
 */
public class SLNode {
    private Object element;//数据域
    private SLNode next;//指针域


    public SLNode(Object element, SLNode next) {
        this.element = element;
        this.next = next;
    }

    public Object getData() {
        return element;
    }

    public void setData(Object object) {
         element=object;
    }

    public SLNode getNext() {
        return next;
    }

    public void setNext(SLNode next) {
        this.next = next;
    }
}
