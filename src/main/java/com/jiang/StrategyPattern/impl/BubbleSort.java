package com.jiang.StrategyPattern.impl;

import com.jiang.StrategyPattern.SortStrategy;

import java.util.List;

public class BubbleSort implements SortStrategy {
    @Override
    public List sort(List list) {
        System.out.println("执行了冒泡排序");
        return list;
    }
}
