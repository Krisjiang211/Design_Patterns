package com.jiang.responsibilityChain.teachers;

public class MinTeacher extends Teacher{

    public MinTeacher(Teacher successor) {
        super(3,successor);
    }

    public MinTeacher() {
        super(3,null);
    }

    @Override
    public void handle(int days) {
        if (days<=getDays()){
            System.out.println("最小的老师处理了-----, 让同学请假了"+days+"天");
            return;
        }
        if (getSuccessor()!=null){
            getSuccessor().handle(days);
        }
    }

}
