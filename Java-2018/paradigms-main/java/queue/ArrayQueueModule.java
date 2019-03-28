package queue;

import java.util.Arrays;

public class ArrayQueueModule {
    private static final int maxValue = 200;
    private static Object[] data = new Object[maxValue];
    private static int size = 0, head = 0;

    private static int end() {
        return (head + size) % data.length;
    }

    private static void ensureCapacity() {
        if (size + 1 < data.length) {
            return;
        }
        Object[] temporaryData = new Object[data.length * 5];
        System.arraycopy(data, head, temporaryData, 0, size - end());
        System.arraycopy(data, 0, temporaryData, size - end(), end());
        head = 0;
        data = temporaryData;
    }
   //pre: true
    public static Object[] toArray() {
        if (size == 0) return new Object[0];
        Object[] result = new Object[size];
        int j = 0;
        result = s(result);
        return result;
    }
    //post: R = elements in queue in order from start to end && const
    private static Object[] s(Object[] result) {
        for (int i = 0; data[(i + head) % data.length] != null && i < size; i++) {
            result[i] = data[(i + head) % data.length];
        }
        return result;
    }


    //pre: element != null
    public static void enqueue(Object element) {
        ensureCapacity();
        data[end()] = element;
        size++;
    }
    //post: data[size] = element &&  data'[i] = data[i] for all i in 0..size' - 1
    // && size = size' + 1

    //pre: size > 0
    public static Object dequeue() {
        Object result = element();
        data[head] = null;
        head = (head + 1) % data.length;
        size--;
        return result;
    }
    // Post: (size == size' - 1) && (data'[i] == data[i] for i = {0..size' - 1}/{head'}) && (R == data'[head])

    //pre: size > 0
    public static Object element() {
        return data[head];
    }
    //post: R = e[1], for i = (0..size) data[i] = data'[i] && size' = size

    //pre: true
    public static int size() {
        return size;
    }
    //post: R = size && size = size' && data[i] = data'[i] for i = 0..size - 1

    //pre: true
    public static boolean isEmpty() {
        return size == 0;
    }
    //post: R = size == 0 && for i = (1..size) data[i] = data'[i]

    //pre: true
    public static void clear() {
        size = 0;
        head = 0;
    }
    //post: head == 0 && size == 0
}

