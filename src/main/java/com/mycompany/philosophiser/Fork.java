package com.mycompany.philosophiser;


public class Fork {
    private volatile Philosopher owner = null;
    public void setOwner( Philosopher p) {
        if (owner == null) {
            synchronized (this) {
                if (owner == null) {
                    owner = p;
                }
            }
        }
    }

    public Philosopher getOwner() {
        return owner;
    }
}
