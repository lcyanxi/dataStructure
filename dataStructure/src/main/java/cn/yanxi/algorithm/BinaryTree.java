package cn.yanxi.algorithm;

/**
 * Created by lichang on 2018/7/5
 */
public class BinaryTree {
    private static class Node {
        private int data;
        private Node leftChild;
        private Node rightChild;

        public Node(int data) {
            this.data = data;
        }
    }

    private Node root;

    public void insert(int data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parentNode = null;
        while (current != null) {
            parentNode = current;
            // 当前值比插入值大，搜索左子节点
            if (current.data > data) {
                current = current.leftChild;
                if (current == null) {
                    parentNode.leftChild = newNode;
                    return;
                }
            } else {
                // 当前值比插入值小，搜索右子节点
                current = current.rightChild;
                if (current == null) {
                    parentNode.rightChild = newNode;
                    return;
                }
            }
        }
    }

    /**
     * 查找节点
     *
     * @param key
     * @return
     */
    public Node find(int key) {
        Node current = root;
        while (current != null) {
            if (current.data > key) {
                current = current.leftChild;
            } else if (current.data < key) {
                current = current.rightChild;
            } else {
                return current;
            }
        }
        //遍历完整个树也找不到改关键字
        return null;
    }

    /**
     * 删除节点 分三种情况
     * 1.该节点为叶子节点
     * 2.该节点有一个子节点
     * 3.该节点有两个子节点
     *
     * @param key
     */
    public void delete(int key) {
        Node current = root;
        Node parent = current;
        //表记要删除的节点相对于它的父节点是左还是右
        boolean isLeft = true;
        //找到要删除的节点
        while (current != null && current.data != key) {
            parent = current;
            if (current.data > key) {
                current = current.leftChild;
                isLeft = true;
            } else {
                current = current.rightChild;
                isLeft = false;
            }
        }
        if (current == null) {
            System.out.println("没有找到该节点");
        }
        //删除的为叶子节点
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {
                root = null;
                return;
            }
            if (isLeft) {
                parent.leftChild = null;
                return;
            } else {
                parent.rightChild = null;
                return;
            }
        } else if (current.rightChild == null) {
            //删除的只有一个节点，而且为左节点
            if (current == root) {
                root = current.leftChild;
                return;
            }
            if (isLeft) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        } else if (current.leftChild == null) {
            //删除的只有一个节点，而且为右节点
            if (current == root) {
                root = current.rightChild;
                return;
            }
            if (isLeft) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        } else {
            //删除有两个子节点的节点
            //找到要继承的节点
            Node successor = getSuccessor(current);
            if (current == root) {
                successor = root;
            }
            if (isLeft) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }
            successor.leftChild = current.leftChild;
        }
    }

    /**
     * 后继节点
     * 程序找到删除节点的右节点，(注意这里前提是删除节点存在左右两个子节点，如果不存在则是删除情况的前面两种)，
     * 然后转到该右节点的左子节点，依次顺着左子节点找下去，最后一个左子节点即是后继节点；
     * 如果该右节点没有左子节点，那么该右节点便是后继节点。
     * @param delNode
     * @return
     */
    public Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current != null) {
            successorParent = successor;
            successor = current.leftChild;
            current = current.leftChild;
        }
        // 后继节点不是删除节点的右子节点，将后继节点替换删除节点
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor = delNode.rightChild;
        }
        return successor;
    }

    /**
     * 中序遍历  中序遍历:左子树——》根节点——》右子树
     *
     * @param current
     */
    public void infixOrder(Node current) {
        if (current != null) {
            infixOrder(current.leftChild);
            System.out.println(current.data + "");
            infixOrder(current.rightChild);
        }
    }

    /**
     * 前序遍历:根节点——》左子树——》右子树
     *
     * @param current
     */
    public void preOrder(Node current) {
        if (current != null) {
            System.out.println(current.data);
            preOrder(current.leftChild);
            preOrder(current.rightChild);
        }
    }

    /**
     * 后序遍历:左子树——》右子树——》根节点
     *
     * @param current
     */
    public void postOrder(Node current) {
        if (current != null) {
            postOrder(current.leftChild);
            postOrder(current.rightChild);
            System.out.println(current.data);

        }
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.insert(50);
        bt.insert(20);
        bt.insert(80);
        bt.insert(10);
        bt.insert(30);
        bt.insert(60);
        bt.insert(90);
        bt.insert(25);
        bt.insert(85);
        bt.insert(100);
        System.out.println("中序排序：");
        bt.infixOrder(bt.root);
        bt.delete(85);
        System.out.println("中序排序：");
        bt.infixOrder(bt.root);
    }
}
