package task_1;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class QuickSortTest {

    private QuickSort quickSort;

    @Before
    public void setUp() {
        quickSort = new QuickSort();
    }

    @Test
    public void sortTest() {
        Integer[] test = new Integer[]{1, 5, 3, 8, 2, 9};
        quickSort.sort(test);
        assertArrayEquals(new Integer[]{1, 2, 3, 5, 8, 9}, test);
    }

    @Test
    public void bigSizeSortTest() {
        Integer[] test = new Integer[100_000];
        Random random = new Random();
        for (int i = 0; i < test.length; i++) {
            test[i] = random.nextInt();
        }
        long start = System.nanoTime();
        quickSort.sort(test);
        long end = System.nanoTime() - start;
        System.out.println("время сортировки - " + end);
        for (int i = 0; i < test.length - 1; i++) {
            if (test[i] > test[i + 1]) {
                fail();
            }
        }
    }

    @Test
    public void isSortedTrue() {
        assertTrue(quickSort.isSorted(new Integer[]{1, 2, 3, 5, 7, 9, 11}));
    }

    @Test
    public void isSortedFalse() {
        assertFalse(quickSort.isSorted(new Integer[]{1, 3, 2, 5, 7, 9, 11}));
    }
}