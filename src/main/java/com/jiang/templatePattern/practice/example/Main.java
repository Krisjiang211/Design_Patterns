package com.jiang.templatePattern.practice.example;

import com.jiang.templatePattern.OrderProcessTemplate;
import com.jiang.templatePattern.practice.impl.NetOrder;
import com.jiang.templatePattern.practice.impl.StoreOrder;

public class Main {

    public static void main(String[] args) {
        //我想使用网上支付的流程
        OrderProcessTemplate orderProcessTemplate = new NetOrder();
        orderProcessTemplate.processOrder();

        System.out.println("----------------------------------");

        //我想使用线下支付的流程
        orderProcessTemplate = new StoreOrder();
        orderProcessTemplate.processOrder();
    }

}
