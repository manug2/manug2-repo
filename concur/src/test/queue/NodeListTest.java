package queue;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by maverick on 5/15/2016.
 */
public class NodeListTest {

    NodeList<Integer> list;
    @Before
    public void setUp() {
        list = new NodeList<>();
    }

    @Test
    public void should_add_item() {
        list.add(100);
    }

    @Ignore
    public void should_give_null_when_remove_from_empty() {
        assertEquals(null, list.remove());
    }

    @Test
    public void should_remove_when_more_than_1_items() {
        list.add(100);
        list.add(200);
        assertEquals(new Integer(100), list.remove());
    }

    @Test
    public void should_remove_when_only_1_item() {
        list.add(200);
        assertEquals(new Integer(200), list.remove());
        assertTrue(list.isEmpty());
    }

    @Test
    public void should_remove_twice() {
        list.add(100);
        list.add(200);
        assertEquals(new Integer(100), list.remove());
        assertEquals(new Integer(200), list.remove());
        assertTrue(list.isEmpty());
    }

    @Test
    public void should_be_empty_after_interleaving_add_remove() {
        list.add(100);
        assertEquals(new Integer(100), list.remove());
        list.add(200);
        assertEquals(new Integer(200), list.remove());
        list.isEmpty();
    }

    @Test
    public void should_be_empty_after_add_remove_operations() {
        list.add(100);
        list.add(200);
        list.remove();
        list.remove();
        assertTrue(list.isEmpty());
    }

}
