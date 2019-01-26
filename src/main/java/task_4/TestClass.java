package task_4;

import task_4.IO.Occurrences;
import task_4.IO.Store;

import java.io.IOException;

/**
 * тест класс
 */
public class TestClass{

    public static void main(String[] args) {

        Store store = new Store();
        Occurrences occurrences = new MegaOccurrences();

        try {
            occurrences.getOccurrences(store.getSources(), store.getWords(), store.getRes());
        } catch (IOException e) {
            System.out.println("поиск не удался...");
        }
    }

}
