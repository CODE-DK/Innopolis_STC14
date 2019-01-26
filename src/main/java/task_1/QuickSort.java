package task_1;

import org.jetbrains.annotations.NotNull;

/**
 * This class implements the {@link IntegerSortable} interface and Quick sort
 * algorithm. The algorithm offers O(n log(n)) performance
 * only {@link java.lang.Integer} arrays.
 * <p>
 * All exposed methods are package-private, designed to be invoked
 * from public methods (@code sort()).
 *
 * @author Dmitriy Komovskiy
 * @version 2019.01.19.001
 */

public class QuickSort implements IntegerSortable {

    /**
     * This is the general sort method for sort (@link java.lang.Integer) arrays only.
     * It also provide a null checking with a message on console if
     * (@link java.lang.NullPointerException) is exist.
     *
     * @param mass the array to be sorted
     */

    @Override
    public void sort(@NotNull Integer[] mass) {
        if (!containsNull(mass)) {
            quickSort(mass, 0, mass.length - 1);
        } else {
            System.out.println("Array contains null");
        }
    }

    /**
     * Sort the array by (one - pivot) Quick sort. Notice that at first you need to check array
     * on sortable with (@code isSorted) method.
     *
     * @param mass  the array to be sorted
     * @param left  the index of the first element, inclusive, to be sorted
     * @param right the index of the last element, inclusive, to be sorted
     */

    private void quickSort(Integer[] mass, Integer left, Integer right) {

        /*
         * return if the array length is 0
         */
        if (mass.length < 2)
            return;

        /*
         * return if there is nothing to divide.
         */
        if (left.compareTo(right) >= 0)
            return;

        /*
         * return if it have sorted already
         */
        if (isSorted(mass)) {
            return;
        }

        /*
         * init the carry element.
         * in this case it init by taking a random value
         * from the middle of exist array
         */
        int carry = mass[left + (right - left) / 2];

        /*
         * divide the array on two sub arrays about the carry element
         */
        int i = left, j = right;
        while (i <= j) {
            while (mass[i] < carry) {
                i++;
            }

            while (mass[j] > carry) {
                j--;
            }

            /*
             * and swap if needs
             */
            if (i <= j) {
                int temp = mass[i];
                mass[i] = mass[j];
                mass[j] = temp;
                i++;
                j--;
            }
        }

        /*
         * recursion call of left and right sub arrays for sort them
         */
        if (left < j)
            quickSort(mass, left, j);

        if (right > i)
            quickSort(mass, i, right);
    }

    /**
     * This {@code isSorted} method returns true when the array have already sorted.
     * We recommend to use this method before sort to be sure that
     * array is unsorted. After this you can sort array with internal method
     *
     * @param mass the array to be checked
     * @return true if array have already sorted.
     */

    @Override
    public boolean isSorted(Integer[] mass) {

        for (int i = 0; i < mass.length - 1; i++) {

            if (mass[i] > mass[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private boolean containsNull(Integer[] mass) {
        for (Integer integer : mass) {
            if (integer == null) return true;
        }
        return false;
    }
}
