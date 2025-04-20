package com.jiang.responsibilityChain.teachers;

import lombok.Data;

@Data
public abstract class Teacher {
    private int days;//可以允许的最大请假天数
    private Teacher successor;//更高继任者

    public Teacher(int days, Teacher successor) {
        this.days = days;
        this.successor = successor;
    }

    //请假执行
    public abstract void handle(int days);

}
