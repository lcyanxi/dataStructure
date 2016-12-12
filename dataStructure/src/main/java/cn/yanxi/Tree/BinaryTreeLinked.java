package cn.yanxi.Tree;

import cn.yanxi.List.ListDao;
import cn.yanxi.List.linkedList.LinkedListImpl;
import cn.yanxi.iterator.Iterator;

import java.util.LinkedList;

/**
 * Created by lcyanxi on 16-12-13.
 */
public class BinaryTreeLinked implements Tree {

    public BinTreeNode find(Object object) {
        return null;
    }

    public Iterator preOrder() {
        ListDao listDao=new LinkedListImpl();

        return null;
    }

    public Iterator inOrder() {
        return null;
    }

    public Iterator postOrder() {
        return null;
    }

    public Iterator levelOrder() {
        return null;
    }

    private void preOrderRecursion(BinTreeNode node, LinkedList list){
        if (node==null) return;
        list.add(node);
        preOrderRecursion(node.getLchild(),list);
        preOrderRecursion(node.getRchild(),list);
    }

}
