package cn.yanxi.queue;

/**
 * Created by lcyanxi on 16-12-10.
 */
public interface Queue {
    /**
     *
     * @return 返回队列的大小
     */
    public int getSize();

    /**
     *
     * @return 判断队是否为空
     */
    public  boolean isEmpty();

    /**
     *
     * @param object 入队
     */
    public void enQueue(Object object);

    /**
     *
     * @return
     * @throws QueueEmptyException 出队
     */
    public Object deQueue() throws QueueEmptyException;

    /**
     *
     * @return
     * @throws QueueEmptyException 获得队首元素
     */
    public Object getFont() throws  QueueEmptyException;
}
