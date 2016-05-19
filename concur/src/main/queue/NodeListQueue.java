package queue;

/**
 * Created by maverick on 5/15/2016.
 */
public class NodeListQueue extends MyBlockingQueue {

    NodeList<Integer> list;
    public NodeListQueue() {
        list = new NodeList<>();
    }

    @Override
    public boolean add(Integer item) {
        list.add(item);
        return true;
    }

    @Override
    public Integer take() throws InterruptedException {
        return list.remove();
    }

    @Override
    public MyBlockingQueue clone() {
        return new NodeListQueue();
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
