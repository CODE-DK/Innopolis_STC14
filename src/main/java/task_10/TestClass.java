package task_10;

import java.util.ArrayList;

public class TestClass {
    /**
     *  метод завершиться с ошибкой
     *  OutOfMemoryError c пометкой Java Heap Space.
     */
    public void javaHeapSpace() {
        ArrayList<String> anyArray = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            anyArray.add(builder.append(i).toString());
        }
    }
}
