package cn.yanxi.stack;

/**
 * Created by lcyanxi on 16-12-8.
 */
public interface Stack {

    /**
     *
     * @return 返回栈的大小
     */
    public int getSize();

    /**
     *
     * @return 判断栈是否为空  true为空
     */
    public boolean isEmpty();

    /**
     *
     * @param object 入栈
     */
    public void push(Object object);

    /**
     *
     * @return 出栈
     */
    public Object pop() throws  StackEmptyException;

    /**
     *
     * @return 获取栈顶元素
     */
    public Object getTop() throws StackEmptyException;

    /**
     *
     * @return 显示栈中元素
     */
    public Object[] showData();
}


























