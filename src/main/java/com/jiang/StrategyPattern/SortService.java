package com.jiang.StrategyPattern;

import java.util.List;

/**
 * 行为型的设计模式玩的就是  多态
 * 策略模式 跟 模版模式很像, 但是区别是, 模版模式从宏观角度控制流程, 策略模式从微观角度控制流程..模版模式中可以嵌入策略模式一起用
 */
//如果想使用我的SortService,那么必须传入一个SortStrategy(也就是排序方式)
//传完之后,可直接调用sort函数进行排序(也就是使用SortService的功能)
public class SortService {

    private SortStrategy sortStrategy;

    public SortService(SortStrategy sortStrategy) {
        this.sortStrategy=sortStrategy;
    }

    public List<Integer> sort(List<Integer> list) {
        return sortStrategy.sort(list);
    }
}
