package com.douguo.ndc;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/23
 */
public class StaticTest {
    public static void main(String[] args) {
        String a="a";
        String b="aab";
        String c =a+"ab";
        System.out.println(c==b);
        System.out.println(c.equals(b));

        staticFunction();

        B a1=new B();
        System.out.println(a1.a);
        a1.say();
    }
    static int b = 112;
    static {
        System.out.println("1");
    }

   static StaticTest st = new StaticTest();


    {
        System.out.println("2");
    }

    StaticTest() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b+",c="+c);
    }

    public static void staticFunction() {
        System.out.println("4");
    }

    int a = 110;
    final static int c = 120;
}

class A{

    int a=5;

    public void say(){
        System.out.println(a);
    }

    public A(){
        method();
    }

    public void method(){
        System.out.println("aaaaaaaaa");
    }

}

class B extends A{
    int a=10;

    @Override public void say() {
        System.out.println(a);
    }


    public B(){
       method();
    }

    public void method(){
        System.out.println("bbbbbbb");
    }

}