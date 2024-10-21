package com.jiang.StrategyPattern.example;

import com.jiang.StrategyPattern.SortService;
import com.jiang.StrategyPattern.impl.BubbleSort;
import com.jiang.StrategyPattern.impl.QuickSort;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        //假如我想用冒泡排序表
        SortService sortService = new SortService(new QuickSort());
        sortService.sort(List.of(1,2,3,4,5));

        System.out.println("---------------------------------");


        //假如我想用快速排序
        sortService = new SortService(new BubbleSort());
        sortService.sort(List.of(1,2,3,4,5));
    }
}
