package com.jiang.responsibilityChain.teachers;

public class MiddleTeacher extends Teacher{
    public MiddleTeacher( Teacher successor) {
        super(7, successor);
    }

    public MiddleTeacher() {
        super(7,null);
    }
    @Override
    public void handle(int days) {
        if (days<=getDays()){
            System.out.println("一般级别的老师处理了-----, 让同学请假了"+days+"天");
            return;
        }
        if (getSuccessor()!=null){
            getSuccessor().handle(days);
        }
    }
}
