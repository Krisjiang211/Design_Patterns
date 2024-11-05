package com.jiang.MakeClassPattern.SinglePattern;


//枚举实现单例模式
public enum Singleton {
    INSTANCE;

    public void methodA(){
        System.out.println("枚举实现单例模式");
    }
}
