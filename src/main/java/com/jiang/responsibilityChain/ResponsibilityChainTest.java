package com.jiang.responsibilityChain;

import com.jiang.responsibilityChain.teachers.BigTeacher;
import com.jiang.responsibilityChain.teachers.Entrance;
import com.jiang.responsibilityChain.teachers.MiddleTeacher;
import com.jiang.responsibilityChain.teachers.MinTeacher;

public class ResponsibilityChainTest {


    public static void main(String[] args) {
        Entrance entrance = new Entrance();

        entrance.apply(1);
        entrance.apply(5);
        entrance.apply(10);
        entrance.apply(20);

    }
}
