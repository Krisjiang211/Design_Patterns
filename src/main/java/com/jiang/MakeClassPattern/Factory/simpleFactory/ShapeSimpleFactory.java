package com.jiang.MakeClassPattern.Factory.simpleFactory;

import com.jiang.MakeClassPattern.Factory.simpleFactory.impl.Circle;
import com.jiang.MakeClassPattern.Factory.simpleFactory.impl.Rectangle;

import java.util.Properties;

public class ShapeSimpleFactory {

    public Shape createShape(Shape type){

        if (type.equals("Circle")){
            return new Circle();
        } else if (type.equals("Rectangle")) {
            return new Rectangle();
        }
        return null;
    }
}
