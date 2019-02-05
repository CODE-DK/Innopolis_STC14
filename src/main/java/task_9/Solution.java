package task_9;

import org.apache.log4j.Logger;
import task_9.IO.SReader;
import task_9.IO.SWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

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

    private static final Logger LOGGER = Logger.getLogger(Solution.class);
    private static final String PROPERTIES = "./src/main/resources/loader.properties";

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(PROPERTIES));
        } catch (IOException e) {
            LOGGER.debug("Не могу прочитать файл свойств");
        }
        Solution solution = new Solution();
        Loader loader = new LoaderImpl(properties);
        solution.getOccurrences(loader.loadSources(), loader.loadWords(), loader.getOutDir());
    }

    /**
     * основной метод для запуска работы программы, по переданному массиву ссылок
     * создает небходимое число потоков, занимающихся поском вхождений слов в предложение
     * из переданного массива слов words.txt
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
        try {
            FileWriter fw = new FileWriter(res);
            Thread writer = new SWriter(fw, queue);
            writer.start();
            waitAllThreads(ex);
            writer.interrupt();
        } catch (IOException e) {
            LOGGER.debug("Ошибка при записи в файл ", e);
        }

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
