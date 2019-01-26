package task_2;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * test class
 */
public class TestClass {

    public static void main(String[] args) {
        testOne();
        testTwo();
    }
    private static void testTwo() {
        System.out.println("\n-----задание 2------");

        Object[] mass = {1, 5, 3, 7, 2, 8, 6, 3, 9};

        ObjectBox<Object> objectBox = new ObjectBox<>(mass);

        System.out.println("Исходный массив");
        System.out.println(Arrays.toString(mass));

        System.out.println("\n-----addObject()------");

        System.out.println("try to add 1 " + objectBox.addObject(1));
        System.out.println("try to add 4 " + objectBox.addObject(4));
        System.out.println("try to add 3 " + objectBox.addObject(3));
        System.out.println("try to add 6 " + objectBox.addObject(6));
        System.out.println("try to add 9 " + objectBox.addObject(9));

        System.out.println("\n-----deleteObject()------");

        System.out.println("try to delete 4 " + objectBox.deleteObject(4));
        System.out.println("try to delete 4 " + objectBox.deleteObject(4));

        System.out.println("\n-----dump()------");

        System.out.println(objectBox.dump());
    }
    private static void testOne() {
        System.out.println("-----задание 1------\n");

        Long[] mass1 = new Long[]{1L, 4L, 8L, 2L, 4L, 3L, 6L, 8L, 4L, 2L, 6L};

        Set<Byte> byteList = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            byteList.add((byte) i);
        }

        MathBox<Long> m1 = new MathBox<>(mass1);
        System.out.println(m1);

        System.out.println("addObject " + m1.addObject(10L));
        System.out.println("deleteObject " + m1.deleteObject(1L));
        System.out.println("Dump " + m1.dump());
        System.out.println("Summator " + m1.summator());
        System.out.println("Splitter " + m1.splitter(2));
        System.out.println("add new Object() " + m1.addObject(new Object()));

        System.out.println("\n---------*****------------\n");
        System.out.println("----------hashCode()----------");

        for (int i = 0; i < 10; i++) {
            System.out.println(m1.hashCode());
        }
    }
}
