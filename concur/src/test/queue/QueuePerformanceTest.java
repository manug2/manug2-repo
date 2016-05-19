package queue;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by maverick on 5/15/2016.
 */
public class QueuePerformanceTest {
    final BlockingQueue<Integer> queue;
    final int num_of_items;
    final String testName;

    public QueuePerformanceTest(int num_of_items, BlockingQueue<Integer> queue) {
        this.num_of_items = num_of_items;
        this.queue = queue;
        this.testName = String.format("%sm-%s", num_of_items/1000000, queue);
    }


    private static int durationInMS(long end, long start) {
        return (int) ((end-start)/1000000);
    }

    @Test
    public void should_be_able_to_take_one_element_when_put() throws InterruptedException, TimeoutException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Long> service = new ExecutorCompletionService<>(es);

        Future<Long> fr = service.submit(new ItemReceivingCallable());
        Future<Long> fs = service.submit(new ItemSendingCallable());
        final long start = System.nanoTime();

        long end;
        long sent_count, received_count;

        sent_count = fr.get(10, TimeUnit.SECONDS);
        received_count = fs.get(10, TimeUnit.SECONDS);
        end = System.nanoTime();
        es.shutdown();

        if (sent_count != num_of_items)
            throw new RuntimeException(testName + " -> all not sent");
        if (received_count != num_of_items)
            throw new RuntimeException(testName + " -> all not received");

        int duration = durationInMS(end, start);
        int thruput = throughput(duration);
        System.out.println(
            String.format(
                "%s -> duration [%s], throughput [%s]",
                testName, duration, thruput));
    }

    private int throughput(int durationInMs) {
        return (int) (num_of_items/durationInMs);
    }

    class ItemSendingCallable implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long sent=0;
            //long start=System.nanoTime();
            for (int i = 0; i < num_of_items; i++) {
                if( ! queue.add(i))
                    throw new RuntimeException(String.format(
                            "could not add value at index [%d]", i));
                sent++;
            }
            /*
            int duration = durationInMS(System.nanoTime(), start);
            System.out.println(
                String.format(
                    "%s -> Sent [%s], Time [%s] ms, throughput [%s] K items/s",
                        testName, sent, duration, throughput(duration)));
            */
            return sent;
        }
    }

    class ItemReceivingCallable implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long received = 0;
            //long start=System.nanoTime();
            try {
                for (int i=0; i < num_of_items; i++) {
                    int got = queue.take();
                    if(got != i)
                        throw new RuntimeException(String.format(
                                "incorrect value [%d] received at index [%d]", got, i));
                    received++;
                }
            } catch (InterruptedException e) {
                System.out.println("taker got interrupted");
                e.printStackTrace();
            } finally {
                /*
                int duration = durationInMS(System.nanoTime(), start);
                System.out.println(
                        String.format(
                                "%s -> Received [%s], Time [%s] ms, throughput [%s] K items/s",
                                testName, received, duration, throughput(duration)));
                                */
            }
            return received;
        }
    }
}
