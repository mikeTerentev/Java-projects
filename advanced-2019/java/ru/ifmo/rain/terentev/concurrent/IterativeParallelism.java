package ru.ifmo.rain.terentev.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;
import info.kgeorgiy.java.advanced.concurrent.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.ifmo.rain.terentev.concurrent.Units.joinTasks;

public class IterativeParallelism implements ListIP, ScalarIP {
    private final ParallelMapper mapper;

    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    public IterativeParallelism() {
        this.mapper = null;
    }

    private <T, Res> Res baseTask(int threads, final List<? extends T> list,
                                  final Function<? super Stream<? extends T>, ? extends Res> task,
                                  final Function<? super Stream<? extends Res>, ? extends Res> collect) throws InterruptedException {
        if (threads <= 0) {
            throw new IllegalArgumentException("Number of threads must be positive");
        }
        threads = Math.max(1, Math.min(threads, list.size()));
        final List<Stream<? extends T>> tasks = new ArrayList<>();
        final int groupSize = list.size() / threads;
        int extraTasks = list.size() % threads;
        for (int i = 0, end = 0; i < threads; i++) {
            int begin = end;
            if (extraTasks > 0) {
                end = begin + groupSize + 1;
            } else {
                end = begin + groupSize;
            }
            extraTasks--;
            tasks.add(list.subList(begin, end).stream());
        }
        List<Res> subResult;
        if (mapper != null) {
            subResult = mapper.map(task, tasks);
        } else {
            subResult = new ArrayList<>(Collections.nCopies(threads, null));
            List<Thread> workers = new ArrayList<>();
            for (int i = 0; i < threads; i++) {
                final int pos = i;
                Thread th = new Thread(() -> subResult.set(pos, task.apply(tasks.get(pos))));
                workers.add(th);
                th.start();
            }
            joinTasks(workers);
        }
        return collect.apply(subResult.stream());
    }


    /**
     * Takes  {@link List} and finds biggest element using given amount of threads
     *
     * @param i          amount of threads
     * @param list       {@link List} data
     * @param comparator {@link Comparator} comparator to use
     * @return maximum value
     */
    @Override
    public <T> T maximum(int i, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        final Function<Stream<? extends T>, ? extends T> streamMax = stream -> stream.max(comparator).get();
        return baseTask(i, list, streamMax, streamMax);
    }

    /**
     * Takes  {@link List} and finds smallest element using given amount of threads
     *
     * @param i          amount of threads
     * @param list       {@link List} data
     * @param comparator {@link Comparator} comparator to use
     * @return maximum value
     */
    @Override
    public <T> T minimum(int i, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(i, list, Collections.reverseOrder(comparator));
    }

    /**
     * Takes  {@link List} and {@link Predicate} and checks all element using predicate and given amount of threads
     *
     * @param i         amount of threads
     * @param list      {@link List} data
     * @param predicate {@link Predicate} predicate to use
     * @return true if all elements match the predicate else returns  false
     */
    @Override
    public <T> boolean all(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return !any(i, list, predicate.negate());
    }

    /**
     * Takes  {@link List} and {@link Predicate} and checks all element using predicate and given amount of threads
     *
     * @param i         amount of threads
     * @param list      {@link List} data
     * @param predicate {@link Predicate} predicate to use
     * @return true if any elements match the predicate else returns  false
     */
    @Override
    public <T> boolean any(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return baseTask(i, list,
                stream -> stream.anyMatch(predicate),
                stream -> stream.anyMatch(Boolean::booleanValue)
        );
    }

    /**
     * Takes  {@link List} and join all elements into String  using given amount of threads
     *
     * @param i    amount of threads
     * @param list {@link List} data
     * @return {@link String} concatenated elements
     */
    @Override
    public String join(int i, List<?> list) throws InterruptedException {
        return baseTask(i, list,
                stream -> stream.map(Object::toString).collect(Collectors.joining()),
                stream -> stream.collect(Collectors.joining())
        );
    }

    /**
     * Takes  {@link List} and {@link Predicate} and filter all element using predicate and given amount of threads
     *
     * @param i         amount of threads
     * @param list      {@link List} data
     * @param predicate {@link Predicate} predicate to use
     * @return {@link List} of filtered elements
     */
    @Override
    public <T> List<T> filter(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return baseTask(i, list,
                stream -> stream.filter(predicate).collect(Collectors.toList()),
                stream -> stream.flatMap(Collection::stream).collect(Collectors.toList())
        );
    }


    /**
     * Takes  {@link List} and {@link Predicate} and checks all element using predicate
     *
     * @param i        amount of threads
     * @param list     {@link List} data
     * @param function {@link Function} function to apply to use
     * @return apply {@link Function} to each element and collect it to {@link List}
     */
    @Override
    public <T, U> List<U> map(int i, List<? extends T> list, Function<? super T, ? extends U> function) throws InterruptedException {
        return baseTask(i, list,
                stream -> stream.map(function).collect(Collectors.toList()),
                stream -> stream.flatMap(Collection::stream).collect(Collectors.toList())
        );
    }
}
