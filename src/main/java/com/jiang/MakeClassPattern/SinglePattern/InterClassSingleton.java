package com.jiang.MakeClassPattern.SinglePattern;


//静态内部类实现单例模式
public class InterClassSingleton {

    private InterClassSingleton(){}

    private static class classHolder{
        private static final InterClassSingleton INSTANCE = new InterClassSingleton();
    }

    public InterClassSingleton getInstance(){
        return classHolder.INSTANCE;
    }


}
