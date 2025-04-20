package com.jiang.responsibilityChain.teachers;

public class Entrance {

    private final MinTeacher minTeacher= new MinTeacher();

    public Entrance() {
        MiddleTeacher middleTeacher = new MiddleTeacher();
        minTeacher.setSuccessor(middleTeacher);
        BigTeacher bigTeacher = new BigTeacher();
        middleTeacher.setSuccessor(bigTeacher);
    }

    public void apply(int days){
        minTeacher.handle(days);
    }
}
