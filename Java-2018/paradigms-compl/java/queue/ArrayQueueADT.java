package queue;

public class ArrayQueueADT {
    private final int maxValue = 90;
    private Object[] data = new Object[maxValue];
    private int size = 0, head = 0;

    public static int end(ArrayQueueADT queue) {
        return (queue.head + queue.size) % queue.data.length;
    }

    private static void ensureCapacity(ArrayQueueADT queue) {
        if (queue.size + 1 < queue.data.length) {
            return;
        }
        Object[] temporaryData = new Object[queue.data.length * 2];
        System.arraycopy(queue.data, queue.head, temporaryData, 0, queue.size - queue.end(queue));
        System.arraycopy(queue.data, 0, temporaryData, queue.size - queue.end(queue), queue.end(queue));
        queue.head = 0;
        queue.data = temporaryData;
    }

    //pre: element != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null : "null element";
        queue.ensureCapacity(queue);
        queue.data[queue.end(queue)] = element;
        queue.size++;
    }
    //post: queue.data[size] = element &&  queue.data'[i] = queue.data[i] for all i in 0..size' - 1
    // && queue.size = queue.size' + 1

    //pre: queue.size > 0
    public static Object dequeue(ArrayQueueADT queue) {
        assert !isEmpty(queue) : "queue is empty";
        Object result = element(queue);
        queue.data[queue.head] = null;
        queue.head = (queue.head + 1) % queue.data.length;
        queue.size--;
        return result;
    }
    // Post: (queue.size == queue.size' - 1) && (queue.data'[i] == queue.data[i] for i = {0..queue.size' - 1}/{queue.head'}) && (R == queue.data'[head])

    //pre: true
    public Object[] toArray(Object[] result) {
        if (size == 0) return new Object[0];
        int j = 0;
        result = s(result);
        return result;
    }
    //post: R = elements in queue in order from start to END && const

    private Object[] s(Object[] result) {
        for (int i = 0; data[(i + head) % data.length] != null && i < size; i++) {
            result[i] = data[(i + head) % data.length];
        }
        return result;
    }

    //pre: size > 0
    public static Object element(ArrayQueueADT queue) {
        assert queue.size != 0 : "queue is empty";
        return queue.data[queue.head];
    }
    //post: R = queue.data[queue.head], for i = (0..queue.size) queue.data[i] = queue.data'[i] && queue.size' = queue.size

    //pre: true
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    //post: R = queue.size && queue.size = queue.size' && queue.data[i] = queue.data'[i] for i = 0..queue.size - 1

    //pre: true
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
    //post: R = queue.size == 0 && for i = (1..size) queue.data[i] = queue.data'[i]

    //pre: true
    public static void clear(ArrayQueueADT queue) {

        queue.size = 0;
        queue.head = 0;
    }
    //post: queue.head == 0 && queue.size == 0
}
