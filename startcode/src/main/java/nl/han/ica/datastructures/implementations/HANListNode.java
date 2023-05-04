package nl.han.ica.datastructures.implementations;

public class HANListNode<T> {

    public HANListNode(T storedValue){
        this.storedValue = storedValue;
        this.next = null;
    }

    public HANListNode(T storedValue, HANListNode<T> next){
        this.storedValue = storedValue;
        this.next = next;
    }

    public T storedValue;
    public HANListNode<T> next;
}
