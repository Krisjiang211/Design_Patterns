package com.jiang.visitor.impl;

import com.jiang.visitor.Product;
import com.jiang.visitor.Visitor;

public class DiscountVisitor implements Visitor {
    @Override
    public void visit(Product product) {
        double newProce = product.getPrice();
        if (newProce > 100) {
            newProce=newProce*0.8;
        }
        System.out.println("newPrice = " + newProce);
    }
}
