package queue;

/**
 * Created by maverick on 5/15/2016.
 */
public class NodeList<T> {

    public synchronized void add(T value) {
        Node<T> n = new Node<>(value);

        n.next = tail;

        if (tail != null)
            tail.prev = n;
        tail = n;

        if (head==null)
            head = n;

        notifyAll();
    }

    public synchronized T remove() {
        if (head == null) {
            try {
                wait();
            } catch (InterruptedException ie) {

            }
        }

        T item = head.value;

        Node<T> prev = head.prev;
        head = prev;
        if (prev == null)
            tail = null;

        return item;
    }

    public synchronized boolean isEmpty() {
        return head == null && tail == null;
    }

    Node<T> head, tail;
    class Node<T> {
        final T value;
        Node<T> prev, next;
        public Node (T value) {
            this.value = value;
        }
    }
}
