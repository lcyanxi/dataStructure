package com.douguo.ndc.mytest;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2019/2/19
 */
public class FactoryTest {

    class Apple implements Fruit{
        @Override public void eat() {
            System.out.println("apple");
        }
    }
    class Orange implements Fruit{
        @Override public void eat() {
            System.out.println("Orange");
        }
    }

    public static Fruit getInstance(String ClassName){
        Fruit f=null;
        try{
            f=(Fruit)Class.forName(ClassName).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
}


class  Client{
    public static void main(String args[]){
        Fruit f=FactoryTest.getInstance("com.douguo.ndc.mytest.FactoryTest.Apple");
        if (f!=null){
            f.eat();
        }
    }
}