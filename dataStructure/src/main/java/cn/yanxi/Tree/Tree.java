package cn.yanxi.Tree;

import cn.yanxi.iterator.Iterator;

/**
 * Created by lcyanxi on 16-12-13.
 */
public interface Tree {
//    /**
//     *
//     * @return 返回二叉树的节点数
//     */
//    public  int getSize();
//
//    /**
//     *
//     * @return 判断二叉树是否为空
//     */
//    public  boolean isEmpty();
//
//    /**
//     *
//     * @return 返回二叉树的根节点
//     */
//    public Object getRoot();
//
//    /**
//     *
//     * @return 返回二叉树的高度
//     */
//    public int getHeigth();

    /**
     *
     * @param object
     * @return 查找数据元素e所在的节点
     */
    public  BinTreeNode find(Object object);

    /**
     *
     * @return 先序 、中序、后序、按层遍历
     */
    public Iterator preOrder();
    public Iterator inOrder();
    public Iterator postOrder();
    public Iterator levelOrder();


}
