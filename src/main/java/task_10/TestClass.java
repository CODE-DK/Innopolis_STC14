package task_10;

import java.util.ArrayList;

public class TestClass {
    public static void main(String[] args) {

        ArrayList<String> arr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        int j = 0;
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                j = i;
                arr.add(sb.append(i).toString());
            }
        }catch (OutOfMemoryError e){
            System.out.println(j);
        }
    }
}
