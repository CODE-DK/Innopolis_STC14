package task_9;

import task_9.IO.SReader;
import task_9.IO.SWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * тестовый класс
 * реализует интерфейс по заданию
 */
public class Solution implements Occurrences {

    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();
        solution.getOccurrences(Store.SOURCES, Store.WORDS, Store.RES);
    }

    /**
     *
     * @param sources массив ссылок на внешнии источники
     * @param words массив слов для проверки на вхождение в предложения
     * @param res путь к выходнму файлу для записи предложений
     * @throws IOException
     */
    @Override
    public void getOccurrences(String[] sources, String[] words, String res) throws IOException {
        ExecutorService ex = Executors.newFixedThreadPool(5);
        SWriter writer = new SWriter(res);

        Arrays.stream(sources).forEach(x -> {
            try {
                ex.submit(new SReader(x, writer, new HashSet<>(Arrays.asList(words))));
            } catch (IOException e) {
                System.out.println("e = " + e);
            }
        });
        ex.shutdown();
    }
}
