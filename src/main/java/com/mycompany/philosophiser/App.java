package com.mycompany.philosophiser;


public class App 
{
    public static void main( String[] args )
    {
        int numberOfPhilosophers = 5;
        PhilosopherFactory philosophers = new PhilosopherFactory(numberOfPhilosophers);
        philosophers.run();
        try {
            Thread.sleep(5000);
            philosophers.add(2);
            Thread.sleep(3000);
            philosophers.remove(5);
        } catch (InterruptedException e) {}

    }
}
