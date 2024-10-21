package com.jiang.templatePattern.practice.impl;

import com.jiang.templatePattern.OrderProcessTemplate;

public class NetOrder extends OrderProcessTemplate {
    @Override
    public void doSelect() {
        System.out.println("网上选择商品");
    }


    //这里其实可以后序结合策略模式进一步细化,比如使用 微信支付 或是 支付宝支付...
    @Override
    public void doPayment() {
        System.out.println("进行网络支付");
    }

    @Override
    public void doReceipt() {
        System.out.println("开个电子发票");
    }

    @Override
    public void doDelivery() {
        System.out.println("派送商品");
    }
}
