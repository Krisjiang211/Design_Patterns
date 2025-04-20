package com.jiang.xinangyuan;

import java.util.HashMap;

//享元工厂
public class DogFactory {
    HashMap<String,Dog> map = new HashMap<>();

    public Dog get(String name){
        Dog dog = map.get(name);
        if (dog==null){
            Dog newDog = new Dog(name);
            map.put(name,newDog);
            return newDog;
        }
        return dog;
    }


}
