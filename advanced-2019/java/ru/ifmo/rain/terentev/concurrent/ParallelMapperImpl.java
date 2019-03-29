package ru.ifmo.rain.terentev.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;

public class ParallelMapperImpl implements ParallelMapper {
    private final Queue<Runnable> tasks;
    private final List<Thread> workers;
    private final static int MAX_SIZE = 1_000_000;

    public ParallelMapperImpl(int threads) {
        if (threads <= 0) {
            throw new IllegalArgumentException("Number of threads must be positive");
        }
        tasks = new ArrayDeque<>();
        workers = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        Runnable task;
                        synchronized (tasks) {
                            while (tasks.isEmpty()) {
                                tasks.wait();
                            }
                            task = tasks.poll();
                            tasks.notifyAll();
                        }
                        task.run();
                    }
                } catch (InterruptedException ignored) {
                } finally {
                    Thread.currentThread().interrupt();
                }
            });
            workers.add(thread);
            thread.start();
        }
    }


    private class ResultRepository<T> {
        private List<T> data;
        private int doneSize = 0;

        ResultRepository(int dataSize) {
            this.data = new ArrayList<>(Collections.nCopies(dataSize, null));
        }

        public void set(T value, final int index) {
            synchronized (this) {
                data.set(index, value);
                ++doneSize;
                if (doneSize == data.size()) {
                    notify();
                }
            }
        }

        synchronized List<T> get() throws InterruptedException {
            while (doneSize < data.size()) {
                wait();
            }
            return data;
        }
    }

    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> function, List<? extends T> list) throws InterruptedException {
        ResultRepository<R> resultRepository = new ResultRepository<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            final int index = i;
            createTask(() -> resultRepository.set(function.apply(list.get(index)), index));
        }
        return resultRepository.get();
    }

    private void createTask(final Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    @Override
    public void close() {
        workers.forEach(Thread::interrupt);
        for (Thread thread : workers) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }
}


