package nl.han.ica.datastructures.implementations;

import nl.han.ica.datastructures.IHANLinkedList;

public class HANLinkedList<T> implements IHANLinkedList<T> {

    private final HANListNode<T> header;

    public HANLinkedList(){
         header = new HANListNode<>(null);
    }

    @Override
    public void addFirst(T value) {
        header.next = new HANListNode<>(value, header.next);
    }

    @Override
    public void clear() {
        header.next = null;
    }

    @Override
    public void insert(int index, T value) {
        if(index == 0){
            addFirst(value);
        }
        else{
            HANListNode<T> currentNode = header;
            for(int i = 0; i < index; i++){
                currentNode = currentNode.next;
            }
            currentNode.next = new HANListNode<>(value);
        }
    }

    @Override
    public void delete(int pos) {
        HANListNode<T> currentNode = header;
        for(int i = 0; i < (pos - 1); i++){
            currentNode = currentNode.next;
        }
        currentNode.next = currentNode.next.next;
    }

    @Override
    public T get(int pos) {
        HANListNode<T> currentNode = header;
        for(int i = 0; i < pos; i++){
            currentNode = currentNode.next;
        }
        return currentNode.storedValue;
    }

    @Override
    public void removeFirst() {
        header.next = header.next.next;
    }

    @Override
    public T getFirst() {
        return header.next.storedValue;
    }

    @Override
    public int getSize() {
        HANListNode<T> currentNode = header;
        int size = 0;

        while(currentNode.next != null){
            currentNode = currentNode.next;
            size ++;
        }

        return size;
    }
}
