package com.jiang.MakeClassPattern.Factory.BestPractice;

public class Client {
    public static void main(String[] args) {
        ProductInterface productA = SimpleFactory.createProduct("productA");
        productA.doSomething();
    }
}
