package task_2;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ObjectBoxTest {

    private ObjectBox box;
    private String[] testArray;

    @Before
    public void setUp() {
        testArray = new String[]{"1", "4", "2", "6", "3", "7"};
        box = new ObjectBox(testArray);
    }

    @Test
    public void getList() {
        TreeSet<Object> set = new TreeSet<>();
        Collections.addAll(set, testArray);
        assertEquals(set, box.getList());
    }

    @Test
    public void addObject() {
        box.addObject("12");
        TreeSet<Object> set = new TreeSet<>();
        Collections.addAll(set, testArray);
        set.add("12");
        assertEquals(set, box.getList());
    }

    @Test
    public void deleteObject() {
        box.deleteObject("7");
        TreeSet<Object> set = new TreeSet<>();
        Collections.addAll(set, testArray);
        set.remove("7");
        assertEquals(set, box.getList());
    }

    @Test
    public void dump() {
        Arrays.sort(testArray);
        assertEquals(Arrays.toString(testArray), box.dump());
    }

    @Test
    public void equalsTest() {
        for (int i = 0; i < 1000; i++) {
            if (!new MathBox<>(new Integer[0]).equals(new MathBox<>(new Integer[0]))) {
                fail();
            }
        }
    }

    @Test
    public void hashCodeValidation() {
        for (int i = 0; i < 1000; i++) {
            if (box.hashCode() != box.hashCode()) {
                fail();
            }
        }
    }

    @Test
    public void hashCodeNegativeTest() {
        for (int i = 0; i < 1000; i++) {
            if (new ObjectBox<>(new Integer[0]).hashCode() == new ObjectBox<>(new Integer[0]).hashCode()) {
                fail();
            }
        }
    }
}