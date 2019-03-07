package com.douguo.ndc;

/**
 * Created by lichang on 2018/8/8
 */
public class SingletonTest {
    public static void main(String args[]) {
        Singleton singleton1 = Singleton.getInstance();
        Singleton getSingleton2 = Singleton.getInstance();
        if (singleton1 == getSingleton2) {
            System.out.println("singleton1==getSingleton2");
        } else {
            System.out.println("singleton1!=getSingleton2");
        }

        Singleton3 singleton3 = Singleton3.getInstance();
        Singleton3 getSingleton4 = Singleton3.getInstance();
        if (singleton3 == getSingleton4) {
            System.out.println("singleton3==getSingleton4");
        } else {
            System.out.println("singleton3!=getSingleton4");
        }
    }
}

/**
 * 饿汉模式  不存在线程安全 类加载的时候就已经创建对象了（static）
 * 但我们不希望类加载时创建对象 在类创建的时候加载（延迟加载）---懒汉模式
 */
class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {
        return singleton;

    }
}



/**
 * 懒汉模式  存在线程安全  两个线程同时调用getInstance方法 可能导致重复创建对象
 * 所以用 synchronized 进行加锁 但是synchronized要进行排队，效率低下 所以有了双重检查锁的写法两个if
 * 一般真正需要new的情况很少
 */
class Singleton2 {

    private static Singleton2 singleton;

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        if (singleton == null) {
            synchronized (Singleton2.class) {
                if (singleton == null) {
                    singleton = new Singleton2();
                }
            }
        }
        return singleton;
    }
}

/**
 * 静态内部类
 */
class Singleton3 {
    private Singleton3() {

    }

    public static Singleton3 getInstance() {
        return Holder.singleton3;
    }

    public static class Holder {
        private static Singleton3 singleton3 = new Singleton3();
    }
}
