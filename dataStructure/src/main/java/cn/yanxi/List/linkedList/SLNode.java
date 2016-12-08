package cn.yanxi.List.linkedList;

/**
 * Created by lcyanxi on 16-12-7.
 */
public class SLNode implements Node {

    private  Object element;//抽象数据域
    private SLNode next;//对象的引用作为指针域

    //初始化一个为空的头结点
    public SLNode() {
        this(null ,null);
    }

    public SLNode(Object element, SLNode next) {
        this.element = element;
        this.next = next;
    }

    public SLNode getNext() {
        return next;
    }

    public void setNext(SLNode next) {
        this.next = next;
    }

    public Object getData() {
        return element;
    }

    public void setData(Object object) {
        element=object;
    }
}
