package com.jiang.visitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private double price;

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
