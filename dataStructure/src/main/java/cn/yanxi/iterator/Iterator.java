package cn.yanxi.iterator;

/**
 * Created by lcyanxi on 16-12-8.
 */
public interface Iterator {

    /**
     * 将游标指向第一个元素
     */
    public void  first();

    /**
     * 将游标指向下一个元素
     */
    public void next();

    /**
     *
     * @return 判断迭代器是否还有元素
     */
    public boolean hasNext();

    /**
     *
     * @return 返回当前元素
     */
    public Object currentItem();
}
