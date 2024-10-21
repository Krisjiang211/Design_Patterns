package com.jiang.templatePattern.practice.impl;

import com.jiang.templatePattern.OrderProcessTemplate;


//线下实体店支付逻辑  实现OrderProcessTemplate
public class StoreOrder extends OrderProcessTemplate {
    @Override
    public void doSelect() {
        System.out.println("选择好一个商品");
    }

    @Override
    public void doPayment() {
        System.out.println("现金支付一下");
    }

    @Override
    public void doReceipt() {
        System.out.println("开个发票?");
    }

    @Override
    public void doDelivery() {
        System.out.println("直接把商品移交给你");
    }
}
