package cn.yanxi.stack.stackLinked;

import cn.yanxi.stack.Stack;
import org.junit.Test;

/**
 * Created by lcyanxi on 16-12-9.
 */
public class test {
    @Test
    public void test() throws Exception{
       // Stack stack=new StackLinkedByTop();//带头节点，所有操作在头节点之后
        Stack stack=new StackLinked();//不带头节点，所在操作在头结点上进行
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.getTop());
        stack.pop();
        stack.push(4);
        stack.push(5);
        stack.push(6);
        Object[] objects=stack.showData();
        System.out.print("栈中元素为：");
        for (int i=0;i<objects.length;i++){
            System.out.print(" "+objects[i]);
        }

    }
}
