package task_10;

import java.util.ArrayList;

public class TestClass {
    public static void main(String[] args) {
        javaHeapSpace();
    }

    /**
     *  метод завершиться с ошибкой
     *  OutOfMemoryError c пометкой Java Heap Space.
     */
    private static void javaHeapSpace() {
        ArrayList<String> anyArray = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            anyArray.add(builder.append(i).toString());
        }
    }


}
