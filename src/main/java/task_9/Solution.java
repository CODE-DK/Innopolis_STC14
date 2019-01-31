package task_9;

import task_9.IO.SReader;
import task_9.IO.SWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * реализует интерфейс Occurrences, читает ссылки из входного массива
 * в несколько потоков занимается поиском входжений в слов в предложения
 * каждого из источников, сохраняет все предложения, в которых встречается хотя бы одно из слов
 * списка. Результат в файл data.out.txt
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class Solution implements Occurrences {

    public static void main(String[] args) {
        Solution solution = new Solution();
        try {
            solution.getOccurrences(Store.SOURCES, Store.WORDS, Store.RES);
        } catch (IOException e) {
            System.out.println("e = " + e);
        }
    }

    /**
     * основной метод для запуска работы программы, по переданному массиву ссылок
     * создает небходимое число потоков, занимающихся поском вхождений слов в предложение
     * из переданного массива слов words
     *
     * @param sources массив ссылок на внешнии источники
     * @param words   массив слов для проверки на вхождение в предложения
     * @param res     путь к выходнму файлу для записи предложений
     * @throws IOException
     */
    @Override
    public void getOccurrences(String[] sources, String[] words, String res) throws IOException {
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        ExecutorService ex = Executors.newFixedThreadPool(5);
        Thread writer = new SWriter(res, queue);
        writer.start();

        Arrays.stream(sources).forEach(x -> {
            try {
                ex.submit(new SReader(x, new HashSet<>(Arrays.asList(words)), queue));
            } catch (IOException e) {
                System.out.println("e = " + e);
            }
        });
        ex.shutdown();
        writer.interrupt();
    }
}
