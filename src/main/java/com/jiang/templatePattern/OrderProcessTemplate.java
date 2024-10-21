package com.jiang.templatePattern;


/**
 * 行为型的设计模式玩的就是  多态
 * 模版方法模式从一个宏观的角度来定义一个算法的骨架，而将具体的实现延迟到子类中
 */

public abstract class OrderProcessTemplate {

    public final void processOrder() {
         doSelect();
         doPayment();
         doReceipt();
         doDelivery();
    }

    public abstract void doSelect();
    public abstract void doPayment();
    public abstract void doReceipt();
    public abstract void doDelivery();

}
