package com.mycompany.philosophiser;

public class Philosopher implements Runnable {
    private static String sadPhilosopher = "(⌣́_⌣̀)";
    private static String happyPhilosopher = "(͡° ͜ʖ ͡°)";
    private static String leftForkFace = "ψ(⌣́_⌣̀)";
    private static String rightForkFace = "(⌣́_⌣̀)ψ";
    private static String allForksFace = "ψ͡(° ͜ʖ ͡°)ψ";
    private Fork rightFork=null;
    private Fork leftFork=null;

    public Philosopher(Fork leftFork, Fork rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public void run() {
        rightFork.setOwner(this);
        leftFork.setOwner(this);
    }

    public String toString() {

        if ((leftFork == null) ||(rightFork == null)) return happyPhilosopher;

        if ((leftFork.getOwner() == this) && (rightFork.getOwner() == this)) {
            return allForksFace;
        }
        if (leftFork.getOwner() == this )  {
            return leftForkFace;
        }
        if (rightFork.getOwner() == this) {
            return rightForkFace;
        }
        return sadPhilosopher;
    }

}
