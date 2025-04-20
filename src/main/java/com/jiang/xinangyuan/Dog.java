package com.jiang.xinangyuan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class Dog {
    private String name;

    public Dog(String name) {
        this.name = name;
        print();
    }
    public Dog() {
        print();
    }
    public void print(){
        System.out.println("我被创建啦");
    }

}
