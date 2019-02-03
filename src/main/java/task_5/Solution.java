package task_5;

/**
 * тестовый класс для запуска кастомного пула потоков
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class Solution {

    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool pool = new CustomThreadPool(5);
        for (int i = 0; i < 100; i++) {
            pool.submit(new Thread("Thread " + i));
        }
        pool.shutdown();
    }
}
