package com.jiang.MakeClassPattern.SinglePattern;


//加锁实现单例模式(避免线程问题)

/**
 * 经典三要素:
 * 1.私有化构造方法
 * 2.静态获得实例方法
 * 3.静态实例变量
 */

//双重检查锁定模式(饿汉式)实现单例模式
public class LockSingleton {

    //volatile 关键字用于修饰变量，它的作用是确保变量在多个线程间的可见性，即当一个线程修改了该变量的值，其他线程能够立即看到这个变化。
    private static volatile LockSingleton lockSingleton=null;

    private LockSingleton(){}

    public static LockSingleton getInstance(){
        if (lockSingleton==null){
            synchronized (LockSingleton.class){
                if (lockSingleton==null){
                    lockSingleton=new LockSingleton();
                }
            }
        }
        return lockSingleton;
    }


}
