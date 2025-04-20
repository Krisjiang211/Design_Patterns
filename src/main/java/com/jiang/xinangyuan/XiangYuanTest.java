package com.jiang.xinangyuan;

public class XiangYuanTest {
    public static void main(String[] args) {

        DogFactory factory = new DogFactory();
        Dog dog = factory.get("大黄");
        Dog dog1 = factory.get("大黄");
        Dog dog2 = factory.get("小白");
    }
}
