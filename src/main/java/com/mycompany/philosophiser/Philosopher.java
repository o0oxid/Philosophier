package com.mycompany.philosophiser;

import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
    private static String allForksFace = "ψ(͡° ͜ʖ ͡°)ψ";
    private Fork rightFork=null;
    private Fork leftFork=null;
    private final CountDownLatch startSignal;
    private boolean refresh;

    public Philosopher(Fork leftFork, Fork rightFork, CountDownLatch startSignal) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.startSignal = startSignal;
    }

    public void run() {
        refresh = false;
        aWait();
        long threadId = Thread.currentThread().getId();
        System.out.println("Hi, I'm number " + threadId);
        String philosopherMessage = threadId + ": " + allForksFace;


        boolean leftForkFlag = false;
        boolean rightForkFlag = false;
        // Screen class variables;
        Fork leftFork = this.leftFork;
        Fork rightFork = this.rightFork;

        try {
            while (true) {
                leftForkFlag = leftFork.lock.tryLock();
                rightForkFlag = rightFork.lock.tryLock();
                if (rightForkFlag & leftForkFlag == true) {
                    //got both forks
                    System.out.println(philosopherMessage);
                    Thread.sleep(600);
                }
                //Release fork to avoid deadlocks
                if (rightForkFlag) rightFork.lock.unlock();
                if (leftForkFlag) leftFork.lock.unlock();
                Thread.sleep(300);
                if (refresh) {
                    synchronized (this) {
                        System.out.println(threadId + ": bang refresh event!");
                        leftFork = this.leftFork;
                        rightFork = this.rightFork;
                        refresh = false;
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(threadId + "'s got interrupted.");
        } finally {
            if (rightForkFlag) rightFork.lock.unlock();
            if (leftForkFlag) leftFork.lock.unlock();
        }
    }

    public synchronized Fork getRightFork() {
        return rightFork;
    }
    public synchronized Fork getLeftFork() {
        return leftFork;
    }

    public synchronized void setRightFork(Fork fork) {
        rightFork = fork;
        refresh = true;
    }

    public synchronized void setLeftFork(Fork fork) {
        leftFork = fork;
        refresh = true;
    }

    private void aWait() {
        try {
            startSignal.countDown();
            startSignal.await();
        } catch (InterruptedException e) {}
    }
}
