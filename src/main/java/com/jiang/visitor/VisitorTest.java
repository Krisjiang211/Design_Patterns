package com.jiang.visitor;

import com.jiang.visitor.impl.DiscountVisitor;

public class VisitorTest {
    public static void main(String[] args) {
        Product product = new Product("电脑",6199.99);
        product.accept(new DiscountVisitor());
    }
}
