package com.mycompany.philosophiser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by okhoruzhenko on 2/5/17.
 */
public class PhilosopherFactory {
    private ArrayList<Tuple> factory;
    private int numberOfPhilosophers;
    private CountDownLatch startSignal;

    public PhilosopherFactory(int numberOfPhilosophers) {
        this.numberOfPhilosophers = numberOfPhilosophers;
        factory = new ArrayList<Tuple>(numberOfPhilosophers);
        startSignal = new CountDownLatch(numberOfPhilosophers);
    }

    public void run() {
        ArrayList<Fork> forks = new ArrayList<Fork>(numberOfPhilosophers);

        for (int i = 0; i< numberOfPhilosophers; i++) {
            forks.add(new Fork());
        }

        Philosopher philosopher = new Philosopher(forks.get(forks.size()-1),forks.get(0),startSignal);
        factory.add(new Tuple(philosopher,new Thread(philosopher)));

        for (int i = 1; i< numberOfPhilosophers; i++) {
            philosopher = new Philosopher(forks.get(i-1),forks.get(i),startSignal);
            factory.add(new Tuple(philosopher,new Thread(philosopher)));
        }
        for (Tuple entry: factory) {
            entry.thread.start();
        }
    }

    public void remove(int index) {
        if (index > numberOfPhilosophers) throw new RuntimeException("Index out ouf range.");
        Tuple leftTuple = getLeftTuple(index);
        Tuple rightTuple = getRightTuple(index);

        // Interrupt everything
        factory.get(index).thread.interrupt();
        leftTuple.thread.interrupt();
        rightTuple.thread.interrupt();

        // Remap forks
        leftTuple.philosopher.setRightFork(rightTuple.philosopher.getLeftFork());
        leftTuple.thread.start();
        rightTuple.thread.start();

        //Delete tuple
        factory.remove(index);
    }

    public void add(int index) {
        if (index >= numberOfPhilosophers) throw new RuntimeException("Index out ouf range.");
        Tuple currentTuple = factory.get(index);
        Tuple leftTuple = getLeftTuple(index);

        currentTuple.thread.interrupt();
        leftTuple.thread.interrupt();

        Fork newFork = new Fork();
        Philosopher newPhilosopher = new Philosopher(newFork,currentTuple.philosopher.getLeftFork(),startSignal);

        newPhilosopher.setRightFork(currentTuple.philosopher.getLeftFork());
        newPhilosopher.setLeftFork(newFork);
        leftTuple.philosopher.setRightFork(newFork);

        Tuple newTuple = new Tuple(newPhilosopher,new Thread(newPhilosopher));

        factory.add(index,newTuple);

        currentTuple.thread.start();
        leftTuple.thread.start();
        newTuple.thread.start();
    }

    private Tuple getLeftTuple(int index) {
        return factory.get(index > 0? index - 1: factory.size());
    }

    private Tuple getRightTuple(int index) {
        return factory.get(index < factory.size()? index: 0);
    }

    private class Tuple {
        public Philosopher philosopher;
        public Thread thread;
        public Tuple(Philosopher p ,Thread t) {
            philosopher = p;
            thread = t;
        }
    }
}
