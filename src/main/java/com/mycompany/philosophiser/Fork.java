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

    public void releaseOwner(Philosopher p) {
        if (owner == p) {
            synchronized (this) {
                if (owner == p) {
                    owner = null;
                }
            }
        }
    }

    public Philosopher getOwner() {
        return owner;
    }
}
