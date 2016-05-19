package map;

import org.junit.Test;
import queue.MyBlockingQueue;

import java.util.concurrent.*;

/**
 * Created by maverick on 5/15/2016.
 */
public class MapPerformanceTest {
    final ConcurrentMap<Integer, String> map;
    final int num_of_items;
    final String testName;

    public MapPerformanceTest(int num_of_items, ConcurrentMap<Integer, String> map) {
        this.num_of_items = num_of_items;
        this.map = map;
        this.testName = String.format("%sm-%s", num_of_items/1000000, map.getClass().getSimpleName());
    }

    private static int durationInMS(long end, long start) {
        return (int) ((end-start)/1000000);
    }

    @Test
    public void should_be_able_to_put_and_get() throws InterruptedException, TimeoutException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Long> service = new ExecutorCompletionService<>(es);

        Future<Long> fs = service.submit(new ItemSendingCallable());
        final long sent_start = System.nanoTime();
        long sent_count = fs.get(10, TimeUnit.SECONDS);
        long sent_end = System.nanoTime();
        if (sent_count != num_of_items)
            throw new RuntimeException(testName + " -> all not sent");
        int duration_sent = durationInMS(sent_end, sent_start);
        System.out.println(
                String.format(
                        "%s -> sent duration [%s], throughput [%s], item count [%s].",
                        testName, duration_sent, throughput(duration_sent), map.size()));

        Future<Long> fp = service.submit(new ItemPutIfAbsentCallable());
        final long fp_start = System.nanoTime();
        long fp_count = fp.get(10, TimeUnit.SECONDS);
        long fp_end = System.nanoTime();
        if (fp_count != num_of_items)
            throw new RuntimeException(testName + " -> all not sent");
        int duration_fp = durationInMS(fp_end, fp_start);
        System.out.println(
                String.format(
                        "%s -> put if absent duration [%s], throughput [%s], item count [%s].",
                        testName, duration_fp, throughput(duration_fp), map.size()));

        Future<Long> fr = service.submit(new ItemReceivingCallable());
        final long start = System.nanoTime();
        long end;
        long received_count = fr.get(10, TimeUnit.SECONDS);
        end = System.nanoTime();
        if (received_count != num_of_items)
            throw new RuntimeException(testName + " -> all not received");
        int duration_recieve = durationInMS(end, start);
        System.out.println(
            String.format(
                "%s -> receive duration [%s], throughput [%s], item count [%s]",
                testName, duration_recieve, throughput(duration_recieve), map.size()));
        es.shutdown();

        System.out.println(String.format("items count [%s], throughput [%s]",
                num_of_items, throughput(duration_sent+duration_fp+duration_recieve)));
    }

    private int throughput(int durationInMs) {
        return (int) (num_of_items/durationInMs);
    }

    class ItemPutIfAbsentCallable implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long sent=0;
            //long start=System.nanoTime();
            for (int i = 0; i < num_of_items; i++) {
                final String val = "" + i;
                String put = map.putIfAbsent(i, val);
                if(put == null)
                    throw new RuntimeException(String.format(
                            "previous value not found at index [%d]", i));
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

    class ItemSendingCallable implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long sent=0;
            //long start=System.nanoTime();
            for (int i = 0; i < num_of_items; i++) {
                final String val = "" + i;
                String put = map.put(i, val);
                if(put != null)
                    throw new RuntimeException(String.format(
                            "incorrect previous value[%s] found at index [%d]", put, i));
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
            for (int i=0; i < num_of_items; i++) {
                final String expected = "" + i;
                String got = map.get(i);
                if(!expected.equals(got))
                    throw new RuntimeException(String.format(
                            "incorrect value [%s] got at index [%d]", got, i));
                received++;
            }
            /*
            int duration = durationInMS(System.nanoTime(), start);
            System.out.println(
                    String.format(
                            "%s -> Received [%s], Time [%s] ms, throughput [%s] K items/s",
                            testName, received, duration, throughput(duration)));
                            */
            return received;
        }
    }
}
