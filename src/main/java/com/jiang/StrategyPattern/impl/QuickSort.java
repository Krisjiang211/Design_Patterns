package com.jiang.StrategyPattern.impl;

import com.jiang.StrategyPattern.SortStrategy;

import java.util.List;

public class QuickSort implements SortStrategy {
    @Override
    public List sort(List list) {
        //快速排序
        System.out.println("执行了快速排序");
        return list;
    }
}
