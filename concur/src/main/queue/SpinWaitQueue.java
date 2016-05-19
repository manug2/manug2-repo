package queue;

import java.util.LinkedList;

/**
 * Created by maverick on 4/11/2016.
 */
public class SpinWaitQueue extends MyBlockingQueue {

    @Override
    public boolean add(Integer item) {
        synchronized (this) {
            list.add(item);
            notifyAll();
        }
        return true;
    }

    @Override
    public Integer take() {
        while (true) {
            try {
                synchronized (this) {
                    if (! list.isEmpty())
                        return list.removeFirst();
                    else
                        wait();
                }
            } catch (InterruptedException ie) {
                System.out.println("take() - was interrupted");
            }
        }
    }

    final LinkedList<Integer> list;

    public SpinWaitQueue() {
        list = new LinkedList<>();
    }

    @Override
    public SpinWaitQueue clone() {
        return new SpinWaitQueue();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
