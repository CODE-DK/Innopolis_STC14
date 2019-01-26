package task_1;

import java.util.Arrays;

/**
 * test class
 */
public class TestClass extends Object {

    public static void main(String[] args) {
        Integer[] arr = new Integer[]
                {
                        1, 3, 6, 3,
                        2, 8, 2, 4,
                        null, 3, 2, null,
                        1, 3, 6, 3,
                };

        new QuickSort().sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
