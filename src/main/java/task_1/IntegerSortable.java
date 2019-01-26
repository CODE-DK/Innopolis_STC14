package task_1;

/**
 * This interface provide a view for all classes
 * been used as a class for sort of an (@link java.lang.Integer) arrays
 *
 * @author Dmitriy Komovskiy
 * @version 2019.01.19.001
 */

public interface IntegerSortable {

    /**
     * The sort method provides sort for (@link java.lang.Integer) arrays only.
     * Notice that the input array needs to be checked externally
     * and there is no guaranty to work with any kinds of (@link ava.lang.Throwable)
     *
     * @param mass the array to be sorted
     */

    void sort(Integer[] mass);

    /**
     * This {@code isSorted} method returns true when the array have already sorted.
     *
     * @param mass the array to be checked on sort
     * @return true if array have already sorted.
     */

    boolean isSorted(Integer[] mass);

}
