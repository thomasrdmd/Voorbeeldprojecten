package nl.han.ica.datastructures.implementations;

import nl.han.ica.datastructures.IHANQueue;

public class HANQueue<T> implements IHANQueue<T> {

    HANLinkedList<T> queueList = new HANLinkedList<>();

    @Override
    public void clear() {
        queueList.clear();
    }

    @Override
    public boolean isEmpty() {
        return queueList.getSize() == 0;
    }

    @Override
    public void enqueue(T value) {
        queueList.insert(queueList.getSize(), value);
    }

    @Override
    public T dequeue() {
        T node = queueList.getFirst();
        queueList.removeFirst();
        return node;
    }

    @Override
    public T peek() {
        return queueList.getFirst();
    }

    @Override
    public int getSize() {
        return queueList.getSize();
    }
}
