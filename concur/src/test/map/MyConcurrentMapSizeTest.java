package map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by maverick on 5/14/2016.
 */
public class MyConcurrentMapSizeTest {

    MyConcurrentMap<String, String> map;
    @Before
    public void setUp() {
        map = new MyConcurrentMap<>();
    }

    @Test
    public void should_have_size_1_after_put_entry() {
        map.put("1", "one");
        assertEquals(1, map.size());
    }

    @Test
    public void should_have_size_0_when_empty() {
        assertEquals(0, map.size());
    }

    @Test
    public void should_be_empty_when_nothing_is_put() {
        assertTrue(map.isEmpty());
    }

    @Test
    public void should_not_be_empty_after_entry_put() {
        map.put("1", "one");
        assertFalse(map.isEmpty());
    }

}
