package com.mycompany.philosophiser;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher implements Runnable {
    private static String allForksFace = "ψ(͡° ͜ʖ ͡°)ψ";
    private Fork rightFork=null;
    private Fork leftFork=null;
    private CountDownLatch startSignal;


    public Philosopher(Fork leftFork, Fork rightFork, CountDownLatch startSignal) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.startSignal = startSignal;
    }

    public void run() {
        long threadId = Thread.currentThread().getId();
        System.out.println("Hi, I'm number " + threadId);
        String philosopherMessage = threadId + ": " + allForksFace;
        aWait();

        boolean leftForkFlag = false;
        boolean rightForkFlag = false;

        try {
            while (true) {
                leftForkFlag = leftFork.lock.tryLock();
                rightForkFlag = rightFork.lock.tryLock();
                if (rightForkFlag & leftForkFlag == true) {
                    //got both forks
                    System.out.println(philosopherMessage);
                    Thread.sleep(300);
                }
                //Release fork to avoid deadlocks
                if (rightForkFlag) rightFork.lock.unlock();
                if (leftForkFlag) leftFork.lock.unlock();
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(threadId + "'s got interrupted.");
        } finally {
            if (rightForkFlag) rightFork.lock.unlock();
            if (leftForkFlag) leftFork.lock.unlock();
        }
    }

    public Fork getRightFork() {
        return rightFork;
    }

    public void setRightFork(Fork fork) {
        rightFork = fork;
    }

    public Fork getLeftFork() {
        return leftFork;
    }

    public void setLeftFork(Fork fork) {
        leftFork = fork;
    }


    private void aWait() {
        try {
            startSignal.await();
        } catch (InterruptedException e) {}
    }
}
