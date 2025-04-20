package com.jiang.responsibilityChain.teachers;

public class BigTeacher extends Teacher{
    public BigTeacher(Teacher successor) {
        super(15, successor);
    }

    public BigTeacher() {
        super(15, null);
    }

    @Override
    public void handle(int days) {
        if (days<=getDays()){
            System.out.println("最高级别的老师处理了-----, 让同学请假了"+days+"天");
            return;
        }
        System.out.println("请假天数超过最大天数, 请不了一点");
    }
}
