package task_2;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MathBoxTest {

    private MathBox box;
    private Integer[] testArray;

    @Before
    public void setUp() {
        testArray = new Integer[]{1, 8, 4, 6, 2, 0, 7};
        box = new MathBox(testArray);
    }

    @Test
    public void sumTest() {
        double sum = 0d;
        for (int i = 0; i < testArray.length; i++) {
            sum += testArray[i];
        }

        if (sum != box.summator()){
            fail();
        }
    }

    @Test
    public void addObjectPositiveTest() {
        assertTrue(box.addObject(123));
    }

    @Test (expected = ClassCastException.class)
    public void addObjectNegativeTest() {
        box.addObject("123");
    }

    @Test
    public void splitter() {
        int div = 2;
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] /= div;
        }
        Arrays.sort(testArray);
        assertEquals(Arrays.asList(testArray), box.splitter(div));
    }

    @Test
    public void toStringTest() {
        System.out.println(box);
        assertTrue(box.toString().contains(box.getClass().getSimpleName()));
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
            if (new MathBox<>(new Integer[0]).hashCode() == new MathBox<>(new Integer[0]).hashCode()) {
                fail();
            }
        }
    }
}