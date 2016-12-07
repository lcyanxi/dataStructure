package cn.yanxi.linkedList;

import cn.yanxi.ListDao;
import cn.yanxi.OutOfBoundaryException;

/**
 * Created by lcyanxi on 16-12-7.
 */
public class LinkedListImpl implements ListDao {

    private  int size;//线性表中元素的个数
    private  SLNode head ;//单链表中首节点的引用

    /**
     * 初始化参数
     */
    public LinkedListImpl() {
        size=0;
        head=new SLNode();
    }

    /**
     *
     * @param object  辅助类方法  通过元素object获取它的前驱节点
     * @return
     */
    private  SLNode getPreNode(Object object){
        SLNode p=head;
        while (p.getNext()!=null){
            if(p.getData().equals(object))
                return  p;
            else
                p=p.getNext();
        }
        return null;
    }

    /**
     *
     * @param i
     * @return 获取0<i<size元素所在的前去节点
     */
    private SLNode getPreNode(int i){
        SLNode p=head;
       for (;i>0;i--){
           p=p.getNext();
       }
       return p;
    }

    /**
     *
     * @param i
     * @return   获取0<i<size元素所在节点
     */
    private SLNode getNode(int i){
        SLNode p=head.getNext();
        for (;i>0;i--){
            p=p.getNext();
        }
        return p;
    }



    public int getSeize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(Object object) {
        SLNode p=head.getNext();
        while (p.getNext()!=null){
            if (p.getData().equals(object))
                return true;
            else
                p=p.getNext();
        }
        return false;
    }

    public int indexOf(Object object) {
        SLNode p=head.getNext();
        int index=0;
        while (p.getNext()!=null){
            if (p.getData().equals(object))
                return  index;
            else {
                index++;
                p=p.getNext();
            }
        }
        return -1;
    }

    public void insert(int i, Object object) throws OutOfBoundaryException {
        if (i<0||i>size){
            throw  new OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        SLNode p=getPreNode(i);
        SLNode q=new SLNode(object,p.getNext());
        p.setNext(q);
        size++;
       return;
    }

    public boolean insertBefore(Object p, Object object) {
        return false;
    }

    public boolean insertAfter(Object p, Object object) {
        return false;
    }

    public Object remove(int i) throws OutOfBoundaryException {
        return null;
    }

    public Object replace(int i, Object object) throws OutOfBoundaryException {
        return null;
    }

    public Object get(int i) throws OutOfBoundaryException {
        return null;
    }

    public Object[] gitAll() {
        return new Object[0];
    }

    public void saveData(int i, Object[] objects) {

    }
}
