package com.mycompany.philosophiser;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    public final ReentrantLock lock = new ReentrantLock();
}
