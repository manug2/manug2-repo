package forkJoin;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.*;
import static org.junit.Assert.assertArrayEquals;


public class ForkJoinMergeTest {

    @Test public final void should_return_input_when_one_element() {
        int[] input = new int[] {1};
        new ForkJoinPool().invoke(new ForkJoinMergeSort.SortTask(input));
        assertArrayEquals(new int[] {1}, input);
    }

    @Test public final void should_return_sorted_array_when_input_has_two_elements() {
        int[] input = new int[] {1, 2};
        new ForkJoinPool().invoke(new ForkJoinMergeSort.SortTask(input));
        assertArrayEquals(new int[] {1, 2}, input);
    }

    @Test public final void should_return_sorted_array_when_input_has_two_elements_in_wrong_order() {
        int[] input = new int[] {2, 1};
        new ForkJoinPool().invoke(new ForkJoinMergeSort.SortTask(input));
        assertArrayEquals(new int[] {1, 2} , input);
    }

    @Test public final void should_return_sorted_array_when_input_has_large_number_of_elements() {
        int[] input = new int[] {2, 1, 3, 5, -1, 6, 0};
        new ForkJoinPool().invoke(new ForkJoinMergeSort.SortTask(input));
        System.out.println(Arrays.toString(input));
        assertArrayEquals(new int[]{-1, 0, 1, 2, 3, 5, 6}, input);
    }

    @Test public void executor_service_runnable() {
        ExecutorService s = Executors.newSingleThreadExecutor();
        s.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("hi");
            }
        });
        s.shutdown();
    }

    @Test public void executor_service_callable() {
        ExecutorService s = Executors.newSingleThreadExecutor();
        Future<String> f = s.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hi";
            }
        });
        try {
            System.out.println("furure "+ f.get());
        } catch (ExecutionException|InterruptedException e) {

        }
        s.shutdown();
    }

    @Test public void executor_service_runnable_future() {
        ExecutorService s = Executors.newSingleThreadExecutor();
        Future<?> f = s.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("hi");
            }
        });
        try {
            System.out.println("runnable future "+ f.get());
        } catch (ExecutionException|InterruptedException e) {

        }
        s.shutdown();
    }

    @Test public void execution_completion_service() {
        ExecutorService s = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> cs = new ExecutorCompletionService<String>(s);
        Future<String> f = cs.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hi";
            }
        });
        try {
            System.out.println("furure completion "+ cs.take().get());
        } catch (ExecutionException|InterruptedException e) {

        }

        s.shutdown();
    }

}
