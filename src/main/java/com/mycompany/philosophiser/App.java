package com.mycompany.philosophiser;


public class App 
{
    public static void main( String[] args )
    {
        int numberOfPhilosophers = 10;
        Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
        Fork[] forks = new Fork[numberOfPhilosophers];
        Thread[] lunch = new Thread[numberOfPhilosophers];

        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new Fork();
        }

        philosophers[0] = new Philosopher(forks[numberOfPhilosophers-1], forks[0]);
        lunch[0] = new Thread(philosophers[0]);
        for (int i = 1; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Philosopher(forks[i-1],forks[i]);
            lunch[i] = new Thread(philosophers[i]);
        }
        for (Thread t: lunch) t.start();
        for (Philosopher ph: philosophers) {
            System.out.println(ph);
        }
    }
}
