package cn.yanxi.Tree;

import cn.yanxi.List.ListDao;

import java.util.List;

/**
 * Created by lcyanxi on 16-12-13.
 */
public class BinTreeNode {
    /*
     用三叉链表实现
     */
    private  Object data;//数据域
    private  BinTreeNode parent;//父节点
    private  BinTreeNode lchild;//左孩子
    private  BinTreeNode rchild;//有孩子
    private  int height;//树的高度
    private  int size;//节点的个数

    public BinTreeNode() {
        this(null);
    }

    public BinTreeNode(Object data) {
        this.data = data;
        size=1;
        height=0;
        parent=lchild=rchild=null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BinTreeNode getLchild() {
        return lchild;
    }

    public BinTreeNode getRchild() {
        return rchild;
    }

    public BinTreeNode getParent() {
        return parent;
    }

    public int getSize() {
        return size;
    }

    /**
     *
     * @return 判断是否有父节点
     */
    public  boolean hasParent(){
        return parent!=null;
    }
    public  boolean hasLchild(){
        return  lchild!=null;
    }
    public  boolean hasRchild(){
        return  rchild!=null;
    }

    /**
     *
     * @return 是否为叶子节点
     */
    public  boolean isLeaf(){
        return  !hasRchild()&&!hasRchild();
    }

    /**
     *
     * @return 判断是否为某节点的右孩子
     */
    public boolean isRchild(){
        return hasParent()&&this==parent.rchild;
    }
    public  boolean isLchild(){
        return  hasParent()&&this==parent.lchild;
    }

    public int getHeight(){
        return  height;
    }

    /**
     * 更新当前节点及其祖先的高度
     */
    public  void updateHeight(){
        int newHeight=0;//高度等于左右孩子节点的高度加一中的大者
        if (hasRchild()){
            newHeight=Math.max(newHeight,getRchild().getHeight()+1);
        }
        if (hasLchild()){
            newHeight =Math.max(newHeight,getLchild().getHeight()+1);
        }
        if (newHeight==height){
            return;//说明高度没变，直接放回
        }
        height=newHeight;
        if (hasParent()) {
            getParent().updateHeight();//递归更新父节点的高度
        }
    }

    /**
     * 更新当前节点及其祖先的节点数
     */
    public  void updateSize(){
        size=1;
        if (hasRchild()){
            size+=getRchild().getSize();
        }
        if (hasLchild()){
            size+=getLchild().getSize();
        }
        if (hasParent()) {
            getParent().updateSize();
        }
    }

    /**
     * 断开与父节点的关系
     */
    public  void server(){
        if (!hasParent()) return;
        if (hasRchild()) parent.rchild=null;
        else parent.lchild=null;
        parent.updateSize();//更新节点数
        parent.updateHeight();//更新高度
        parent=null;
    }

    /**
     *
     * @param lc
     * @return   设置当前节点的左孩子，返回原左孩子
     */
    public BinTreeNode setLchild(BinTreeNode lc) {
        BinTreeNode oldLNode=this.lchild;
        if (hasLchild()){
            lchild.server();//断开当前左孩子与节点的关系
        }

        if (lc!=null){
            lc.server();//断开lc与父节点的关系
            this.lchild=lc;//确定父子关系
            lc.parent=this;
            this.updateHeight();
            this.updateSize();
        }

        return oldLNode;
    }

    /**
     *
     * @param rc
     * @return   设置当前节点的右孩子，返回原右孩子
     */
    public BinTreeNode setRchild(BinTreeNode rc) {
        BinTreeNode oldRNode=this.rchild;
        if (hasRchild()){
            rchild.server();
        }
        if (rc!=null){
            rc.server();
            this.rchild=rc;
            rc.parent=this;
            this.updateSize();
            this.updateHeight();
        }
        return  oldRNode;
    }

}

























