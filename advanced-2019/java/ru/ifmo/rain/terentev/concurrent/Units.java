package ru.ifmo.rain.terentev.concurrent;

import java.util.List;

public class Units {
    public static void joinTasks(final List<Thread> threads) throws InterruptedException {
        boolean isInterrupted = false;
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                isInterrupted = true;
            }
        }
        if (isInterrupted) {
            throw new InterruptedException("Some thead didn't join");
        }
    }
}
