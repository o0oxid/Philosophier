package com.mycompany.philosophiser;

public class Philosopher implements Runnable {
    private static String sadPhilosopher = "(⌣́_⌣̀)";
    private static String happyPhilosopher = "(͡° ͜ʖ ͡°)";
    private static String leftForkFace = "ψ(⌣́_⌣̀)";
    private static String rightForkFace = "(⌣́_⌣̀)ψ";
    private static String allForksFace = "ψ(͡° ͜ʖ ͡°)ψ";
    private Fork rightFork=null;
    private Fork leftFork=null;

    public Philosopher(Fork leftFork, Fork rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public void run() {
        long threadId = Thread.currentThread().getId();
        System.out.println("Hi, I'm owner number " + threadId);
        String philosopherMessage = threadId + ": " + allForksFace;
        try {
            while(true) {
                rightFork.setOwner(this);
                leftFork.setOwner(this);
                if ((rightFork.getOwner() == this) && (leftFork.getOwner() == this)) {
                    //got both forks
                    System.out.println(philosopherMessage);
                    Thread.sleep(100);
                    rightFork.releaseOwner(this);
                    leftFork.releaseOwner(this);
                } else {
                    //No luck, but release fork to avoid deadlocks
                    rightFork.releaseOwner(this);
                    leftFork.releaseOwner(this);
                }
                Thread.sleep(50);
            }
    } catch (InterruptedException e) {}


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
