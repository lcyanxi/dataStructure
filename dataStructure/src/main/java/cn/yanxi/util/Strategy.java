package cn.yanxi.util;

/**
 * Created by lcyanxi on 17-3-1.
 */
public interface Strategy {
    /**
     *
     * @param object1
     * @param object2
     * @return  object1==object2 返回0
     *          object1>object2 返回1
     *          object1<object2 返回-1
     */
    public int compare(Object object1,Object object2);

}
