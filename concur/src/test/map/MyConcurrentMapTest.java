package map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maverick on 5/14/2016.
 */
public class MyConcurrentMapTest {

    MyConcurrentMap<String, String> map;
    @Before
    public void setUp() {
        map = new MyConcurrentMap<>();
    }

    @Test
    public void should_be_able_to_put_entry() {
        map.put("1", "one");
    }

    @Test
    public void should_contain_key_entry_after_put() {
        map.put("1", "one");
        assertTrue(map.containsKey("1"));
    }

    @Test
    public void should_not_contain_another_key_entry_after_put() {
        map.put("1", "one");
        assertFalse(map.containsKey("2"));
    }

    @Test
    public void should_contain_value_after_put() {
        map.put("1", "one");
        assertTrue(map.containsValue("one"));
    }

    @Test
    public void should_not_contain_another_value_after_put() {
        map.put("1", "one");
        assertFalse(map.containsValue("two"));
    }

    @Test
    public void should_have_key_after_putIfAbsent() {
        map.putIfAbsent("1", "one");
        assertTrue(map.containsKey("1"));
    }

    @Test
    public void should_have_value_after_putIfAbsent() {
        map.putIfAbsent("1", "one");
        assertTrue(map.containsValue("one"));
    }

    @Test
    public void should_return_null_value_after_putIfAbsent() {
        String value = "one";
        assertNull(map.putIfAbsent("1", value));
    }

    @Test
    public void should_return_value_after_put() {
        String value = "one";
        assertNull(map.put("1", value));
    }

    @Test
    public void should_return_same_value_object_after_put() {
        String value = "one";
        assertNull(map.putIfAbsent("1", value));
    }

    @Test
    public void should_return_orig_value_after_putIfAbsent_when_already_existing() {
        String value = "one";
        map.put("1", value);
        assertEquals(value, map.putIfAbsent("1", "two"));
    }

    @Test
    public void should_return_new_value_after_put_when_already_existing() {
        String value = "one";
        map.put("1", value);
        assertEquals(value, map.putIfAbsent("1", "two"));
    }

    @Test
    public void should_get_put_entry() {
        String value = "one";
        map.put("1", value);
        assertEquals(value, map.get("1"));
    }

}
