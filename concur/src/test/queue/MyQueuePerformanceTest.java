package queue;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RunWith(Parameterized.class)
public class MyQueuePerformanceTest extends QueuePerformanceTest {

    public MyQueuePerformanceTest(int num_of_items, BlockingQueue<Integer> queue) {
        super(num_of_items, queue);
    }

    @Parameterized.Parameters(name = "{0}-{1}")
    public static List<Object[]> testSuite() {
        List<Object[]> suite = new ArrayList<>(10);
//        suite.add(new Object[] {1*1000*1000, new NodeListQueue()});
//        suite.add(new Object[] {10*1000*1000, new NodeListQueue()});
//        suite.add(new Object[] {1*1000*1000, new SpinWaitQueue()});
//        suite.add(new Object[] {10*1000*1000, new SpinWaitQueue()});
//        suite.add(new Object[] {1*1000*1000,    new ArrayLockingQueue(1000)});
//        suite.add(new Object[] {10*1000*1000,    new ArrayLockingQueue(1000)});
        suite.add(new Object[] {1*1000,    new ArrayBlockingQueue<Integer>(1000)});
        suite.add(new Object[] {2*1000,    new ArrayBlockingQueue<Integer>(1000)});
        suite.add(new Object[] {10*1000,    new ArrayBlockingQueue<Integer>(10000)});
        /*
        suite.add(new Object[] {10*1000*1000,   new ArrayLockingQueue(100)});
        suite.add(new Object[] {100*1000*1000,  new ArrayLockingQueue(100)});
        suite.add(new Object[] {1*1000*1000,    new ArrayLockingQueue(1000)});
        suite.add(new Object[] {10*1000*1000,   new ArrayLockingQueue(1000)});
        suite.add(new Object[] {100*1000*1000,  new ArrayLockingQueue(1000)});
        suite.add(new Object[] {1*1000*1000,    new ArrayLockingQueue(10000)});
        suite.add(new Object[] {10*1000*1000,   new ArrayLockingQueue(10000)});
        suite.add(new Object[] {100*1000*1000,  new ArrayLockingQueue(10000)});
        suite.add(new Object[] {1*1000*1000,    new ArrayLockingQueue(100000)});
        suite.add(new Object[] {10*1000*1000,   new ArrayLockingQueue(100000)});
        suite.add(new Object[] {100*1000*1000,  new ArrayLockingQueue(100000)});
        */
        return suite;
    }

}
