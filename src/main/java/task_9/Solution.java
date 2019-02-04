package task_9;

import task_9.IO.SReader;
import task_9.IO.SWriter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * реализует интерфейс Occurrences, читает ссылки из входного массива
 * в несколько потоков занимается поиском входжений в слов в предложения
 * каждого из источников, сохраняет все предложения, в которых встречается хотя бы одно из слов
 * списка. Результат в файл data.out.txt_+__+_
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class Solution implements Occurrences {

    public static void main(String[] args) {
        Solution solution = new Solution();
        Loader loader = new LoaderImpl();
        solution.getOccurrences(loader.loadSources(), loader.loadWords(), loader.getOutDir());
    }

    /**
     * основной метод для запуска работы программы, по переданному массиву ссылок
     * создает небходимое число потоков, занимающихся поском вхождений слов в предложение
     * из переданного массива слов words
     *
     * @param sources массив ссылок на внешнии источники
     * @param words   массив слов для проверки на вхождение в предложения
     * @param res     путь к выходнму файлу для записи предложений
     */
    @Override
    public void getOccurrences(String[] sources, String[] words, String res) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        ExecutorService ex = Executors.newFixedThreadPool(sources.length);
        startThreadPool(sources, words, queue, ex);
        initWriter(res, queue, ex);
    }

    private void initWriter(String res, Queue<String> queue, ExecutorService ex) {
        Thread writer = new SWriter(res, queue);
        writer.start();
        waitAllThreads(ex);
        writer.interrupt();
    }

    private void startThreadPool(String[] sources, String[] words, Queue<String> queue, ExecutorService ex) {
        Stream.of(sources)
                .map(x -> new SReader(x, words, queue))
                .forEach(ex::submit);
        ex.shutdown();
    }

    private void waitAllThreads(ExecutorService ex) {
        while (true) {
            if (ex.isTerminated()) {
                break;
            }
        }
    }
}
