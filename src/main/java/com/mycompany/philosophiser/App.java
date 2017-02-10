package com.mycompany.philosophiser;


public class App 
{
    public static void main( String[] args )
    {
        int numberOfPhilosophers = 5;
        PhilosopherFactory philosophers = new PhilosopherFactory(numberOfPhilosophers);
        philosophers.run();
        try {
            System.out.println("Let's sleep 5 seconds");
            Thread.sleep(3000);
            System.out.println("Add one more philosopher");
            philosophers.add(2);
            Thread.sleep(3000);
            System.out.println("Remove philosopher");
            philosophers.remove(5);
        } catch (InterruptedException e) {}

    }
}
