package cn.yanxi;

/**
 * Created by lcyanxi on 16-12-5.
 */
public interface ListDao {
    /**
     * 线性存储适合于查找   链式存储适合于插入和删除
     */


    /**
     *
     * @return 返回线性表的长度
     */
    public int getSeize();

    /**
     *
     * @return
     *  判断线性表是否为空  true为空
     */
    public boolean isEmpty();

    /**
     *
     * @param object
     * @return  判断线性表是否包含某个元素 true为包含
     */
    public boolean contains(Object object);

    /**
     *
     * @param object
     * @return 返回元素在e的位置 ，如果不存在返回-1
     */
    public int  indexOf(Object object );

    /**
     *
     * @param i  将元素object插插到i的位置，若i越界则 报错
     */
    public void insert(int i,Object object) throws OutOfBoundaryException;

    /**
     *
     * @param p
     * @param object
     * @return  将元素object插入插到p之前 true表示成功
     */
    public boolean insertBefore(Object p,Object object);

    /**
     *
     * @param p
     * @param object
     * @return  将元素object插到p之后，成功为true
     */
    public boolean insertAfter(Object p,Object object);

    /**
     *
     * @param i
     * @return 删除线性表中序号为i的元素 ，并返回该元素，若i越界  报错
     */
    public Object remove(int i) throws OutOfBoundaryException;

    /**
     *
     * @param i
     * @param object
     * @return    替换线性表中序号为i的数据为object 并返回原数据  若i越界  报错
     * @throws IndexOutOfBoundsException
     */
    public Object replace(int i ,Object object) throws OutOfBoundaryException;

    /**
     *
     * @param i
     * @return    返回线性表中i位置的数据元素  若i越界 报错
     * @throws IndexOutOfBoundsException
     */
    public Object get(int i) throws OutOfBoundaryException;

    /**
     *
     * @return 返回线性表中所有的数据元素
     */
    public Object[] gitAll();

    /**
     *
     * @param i
     * @param objects 添加一组数据
     */
    public void saveData(int i ,Object[] objects);







}





















