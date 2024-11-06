package com.jiang.MakeClassPattern.Factory.simpleFactory.impl;

import com.jiang.MakeClassPattern.Factory.simpleFactory.Shape;

public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("绘制圆形");
    }
}
