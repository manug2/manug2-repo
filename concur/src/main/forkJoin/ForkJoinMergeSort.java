package forkJoin;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;


public class ForkJoinMergeSort {

    private static void sort_small(int[] input) {
        for (int i=0; i < input.length-1; i++) {
            for (int j=i; j < input.length; j++)
                if (input[j] < input[i]) {
                    int temp = input[i];
                    input[i] = input[j];
                    input[j] = temp;
                }
        }
    }

    static class SortTask extends RecursiveAction {
            private final int[] array;
            private final int lo, hi;
        SortTask(int[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }
        SortTask(int[] array) {
            this(array, 0, array.length);
        }
        private final static int THRESHOLD = 3;

        @Override
        protected void compute() {
            if (hi -lo < THRESHOLD)
                sort_small(array);
            else {
                System.out.println("using fork join");
                int mid = (lo+hi) >>> 1;
                invokeAll(new SortTask(array, lo, mid),
                            new SortTask(array, mid, hi));
                merge (lo, mid, hi);
            }
        }
        protected void merge(int lo, int mid, int hi) {
            int[] buf = Arrays.copyOfRange(array, lo, mid);
            for (int i=0, j=lo, k=mid; i < buf.length; j++) {
                array[j] = (k==hi || buf[i] < array[k]) ? buf[i++] : array[k++];
            }
        }
    }
}
