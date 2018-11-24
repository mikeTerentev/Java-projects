package queue;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();


    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object dequeue() {
        assert size > 0;
        Object r = element();
        dequeueImpl();
        size--;
        return r;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = dequeue();
            enqueue(result[i]);
        }
        return result;
    }

    protected abstract void dequeueImpl();


    protected abstract AbstractQueue createCopy();

}
