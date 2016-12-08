package cn.yanxi.stack;

/**
 * Created by lcyanxi on 16-12-8.
 */
public class StackArray implements  Stack {

    private final int LEN=10;
    private int top;//栈顶指针
    private Object[] elements;

    public StackArray() {
        top=-1;
        elements=new Object[LEN];
    }

    /**
     * 动态扩容
     */
    private  void  expandSpace(){
            Object[] objects=new Object[elements.length*2];
            for (int i=0;i<elements.length;i++){
                objects[i]=elements[i];
            }
        elements=objects;
    }

    public int getSize() {
        return top+1;
    }

    public boolean isEmpty() {
        return top<0;
    }

    public void push(Object object) {
        if (getSize()>=elements.length){
            expandSpace();
        }
        top++;
        elements[top]=object;


    }

    public Object pop() throws StackEmptyException {
        if (getSize()<0){
            throw  new StackEmptyException("该栈为空！！！");
        }
        Object object=elements[top];
        elements[top]=null;
        top--;
        return object;
    }

    public Object getTop() throws StackEmptyException {
        if (getSize()<0){
            throw  new StackEmptyException("该栈为空！！！");
        }

        return elements[top];
    }

    /**
     *
     * @return 显示栈中元素
     */
    public Object[] showData(){
        Object[] objects=new Object[getSize()];
        for (int i=0;i<getSize();i++){
            objects[i]=elements[i];
        }
        return objects;
    }
}
