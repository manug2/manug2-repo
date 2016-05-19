package map;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import queue.MyBlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RunWith(Parameterized.class)
public class MyMapPerformanceTest extends MapPerformanceTest {

    public MyMapPerformanceTest(int num_of_items, ConcurrentMap<Integer, String> map) {
        super(num_of_items, map);
    }

    @Parameterized.Parameters(name = "{0}-{1}")
    public static List<Object[]> testSuite() {
        List<Object[]> suite = new ArrayList<>(10);
        suite.add(new Object[] {1*1000*1000, new MyConcurrentMap<Integer, String>()});
        suite.add(new Object[] {1*1000*1000, new ConcurrentHashMap<Integer, String>()});
        return suite;
    }

}
