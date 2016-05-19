package queue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MyQueueTest {

    MyBlockingQueue queue;

    @Before
    public void setup() {
        this.queue = new ArrayLockingQueue(10);
    }

    @Test
    public void should_be_able_to_put_one_element_when_empty() throws InterruptedException {
        assertTrue(queue.add(10001));
    }

    @Test
    public void should_be_able_to_take_one_element_when_put() throws InterruptedException {
        queue.add(10001);
        assertEquals(new Integer(10001), queue.take());
    }

    @Test()
    public void should_block_when_taking_from_new_empty_queue() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread taker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int item = queue.take();
                    System.out.println("taken item = " + item);
                    latch.countDown();
                } catch(InterruptedException e) {
                    //
                }
            }
        });
        taker.start();
        assertFalse("it seems an item was taken from empty queue",
                latch.await(100, TimeUnit.MILLISECONDS));

        taker.interrupt();
    }

}
