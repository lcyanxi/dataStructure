package cn.yanxi.List.linkedList;

import cn.yanxi.List.ListDao;
import cn.yanxi.List.OutOfBoundaryException;

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
        while (p!=null){
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
        while (p!=null){
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

    public boolean insertBefore(Object data, Object object) {
        SLNode p=getPreNode(object);//找到要插入的目标元素的前驱
       if (p!=null){
           SLNode q=new SLNode(data,p.getNext());
           p.setNext(q);
           size++;
           return true;
       }

        return false;
    }

    public boolean insertAfter(Object data, Object object) {
        SLNode p=head.getNext();
        while (p!=null){
            if (p.getData().equals(object)){
                SLNode q=new SLNode(data,p.getNext());//找到目标元素
                p.setNext(q);
                size++;
                return  true;
            }
            else p=p.getNext();

        }
        return false;
    }

    public Object remove(int i) throws OutOfBoundaryException {
        if (i<0||i>size){
            throw new OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        SLNode p=getPreNode(i);
        p.setNext(p.getNext().getNext());
        size--;

        return p.getNext().getData();
    }

    public Object replace(int i, Object object) throws OutOfBoundaryException {
        if (i<0||i>size){
            throw new OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        Object target=getNode(i).getData();
        getNode(i).setData(object);
        return target;
    }

    public Object get(int i) throws OutOfBoundaryException {
        if (i<0||i>size){
            throw new OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        return getNode(i).getData();
    }

    public Object[] gitAll() {
        SLNode p=head.getNext();
        Object[] objects=new Object[size];
        for (int i=0;i<size;i++){

            objects[i]=p.getData();
            p=p.getNext();
        }
        return objects;
    }

    public void saveData(int i, Object[] objects) {
        size=i;
        for (int j=0;j<objects.length;j++){
            SLNode p=getPreNode(j);
            SLNode q=new SLNode(objects[j],p.getNext());
            p.setNext(q);
        }
    }
}
