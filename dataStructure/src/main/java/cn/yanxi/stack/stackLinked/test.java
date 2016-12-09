package cn.yanxi.stack.stackLinked;

import cn.yanxi.stack.Stack;
import org.junit.Test;

/**
 * Created by lcyanxi on 16-12-9.
 */
public class test {
    @Test
    public void test() throws Exception{
        Stack stack=new StackLinkedByTop();
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
