package queue;

/**
 * Created by maverick on 4/11/2016.
 */
public class ArrayLockingQueue extends MyBlockingQueue {

    @Override
    public boolean add(Integer item) {
        synchronized (locker) {
            while (true) {
                if (isFull()) {
                    waitOnLock();
                } else {
                    if (++tail == array.length)
                        tail = 0;

                    array[tail] = item;
                    int ret = array[tail];
                    locker.notifyAll();
                    return true;
                }
            }
        }
    }

    private boolean isFull() {
        return (tail == head-1)
                || (head==0 && tail == (array.length-1));
    }

    private void waitOnLock() {
        try {
            locker.wait();
        } catch (InterruptedException e) {
            //implement cancellable
        }
    }

    @Override
    public Integer take() {
        synchronized (locker) {
            while (true) {
                if (isEmpty()) {
                    waitOnLock();
                } else {
                    if (++head == array.length)
                        head = 0;
                    int item = array[head];

                    locker.notifyAll();
                    return item;
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    final Object locker = new Object();
    final int array[];
    int head=-1, tail=-1;

    public ArrayLockingQueue(int size) {
        assert size > 0;
        array = new int[size];
    }

    @Override
    public ArrayLockingQueue clone() {
        return new ArrayLockingQueue(this.array.length);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + array.length + "]";
    }
}
