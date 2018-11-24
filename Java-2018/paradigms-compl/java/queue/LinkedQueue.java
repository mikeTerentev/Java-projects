package queue;

public class LinkedQueue extends AbstractQueue implements Queue {
    private Node firstElement, lastElement;

    public void enqueueImpl(Object element) {
        if (size == 0) {
            firstElement = new Node(element);
            lastElement = firstElement;
        } else {
            Node tmp = new Node(element);
            lastElement.next = tmp;
            lastElement = tmp;
        }
    }

    public Object elementImpl() {
        return firstElement.value;
    }

    public void clearImpl() {
        lastElement = null;
        firstElement = null;
    }

    protected LinkedQueue createCopy() {
        LinkedQueue result = new LinkedQueue();
        Node temp = firstElement;
        while (temp != null) {
            result.enqueue(temp.value);
            temp = temp.next;
        }
        return result;
    }

    public void dequeueImpl() {
        firstElement = firstElement.next;
        if (size == 1) {
            lastElement = null;
        }
    }


}
