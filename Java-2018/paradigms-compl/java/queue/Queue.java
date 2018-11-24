package queue;

public interface Queue {

    //I: size >= 0 && for i = 1..size data[i] != null

    //Pre: element != null
    void enqueue(Object element);
    //Post: data[size] = element && for all i in [0..size) data'[i] = data[i] && size' = size + 1

    //Pre: size > 0
    Object element();
    //Post: R = data[0] &&  for all i in [0..size) data'[i] = data[i] && size' = size

    //Pre: size > 0
    Object dequeue();
    //Post: size' = size - 1 && R = data[0] && for all i in [0..size - 1) data[i] = data'[i + 1]

    //Pre: true
    int size();
    //Post: R = size && size' = size && for all i in [0..size) data'[i] = data[i]

    //Pre: true
    boolean isEmpty();
    //Post: R = (size == 0) && for all i in [0..size) data'[i] = data[i]  && size = size'

    //Pre: true
    void clear();
    //Post: data is empty && size = 0

    //pre:true
    public Object[] toArray();
    //post: R = elements in queue in order from start to END && const
}