package com.jiang.MakeClassPattern.Factory.simpleFactory.impl;

import com.jiang.MakeClassPattern.Factory.simpleFactory.Shape;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("绘制三角形");
    }
}
