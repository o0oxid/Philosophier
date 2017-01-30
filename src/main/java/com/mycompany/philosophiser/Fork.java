package com.mycompany.philosophiser;


public class Fork {
    private Philosopher owner = null;
    public synchronized boolean setOwner( Philosopher p) {
        if (owner == null) {
            owner = p;
            return true;
        }
        return false;
    }
    public synchronized Philosopher getOwner() {
        return owner;
    }
}
