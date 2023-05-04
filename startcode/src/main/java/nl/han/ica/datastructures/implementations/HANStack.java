package nl.han.ica.datastructures.implementations;

import nl.han.ica.datastructures.IHANStack;

public class HANStack<T> implements IHANStack<T> {

    HANLinkedList<T> stackList = new HANLinkedList<>();

    @Override
    public void push(T value) {
        stackList.insert(stackList.getSize(), value);
    }

    @Override
    public T pop() {
        int top = stackList.getSize();
        T node = stackList.get(top);

        stackList.delete(top);

        return node;
    }

    @Override
    public T peek() {
        int top = stackList.getSize();

        return stackList.get(top);
    }
}
