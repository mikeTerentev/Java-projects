package queue;

public class ArrayQueue extends AbstractQueue {
    private Object[] data;
    private int head = 0;

    public ArrayQueue() {
        data = new Object[10];
    }

    public ArrayQueue(int sz) {
        data = new Object[sz];
    }


    public int end() {
        return (head + size) % data.length;
    }

    private void ensureCapacity() {
        if (size + 1 < data.length) {
            return;
        }
        Object[] temporaryData = new Object[data.length * 5];

        temporaryData = s(temporaryData);
        head = 0;
        data = temporaryData;
    }
/*
    public Object[] toArrayOld(Object[] result) {
        if (size == 0) return new Object[0];
        int j = 0;
        result = s(result);
        return result;
    }
*/
    private Object[] s(Object[] result) {
        for (int i = 0; data[(i + head) % data.length] != null && i < size; i++) {
            result[i] = data[(i + head) % data.length];
        }
        return result;
    }

    protected void enqueueImpl(Object element) {
        ensureCapacity();
        data[end()] = element;
    }

    public Object elementImpl() {
        return data[head];
    }


    protected void dequeueImpl() {
        data[head] = null;
        head = (head + 1) % data.length;
        ensureCapacity();
    }

    protected void clearImpl() {
        size = 0;
        head = 0;
    }

    protected ArrayQueue createCopy() {
        ArrayQueue result = new ArrayQueue(data.length);
        result.head = head;
        result.size = size;
        System.arraycopy(data, 0, result.data, 0, data.length);
        return result;
    }


}