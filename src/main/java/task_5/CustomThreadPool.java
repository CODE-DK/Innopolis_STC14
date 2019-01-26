package task_5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Простой кастомный тред пул
 */
public class CustomThreadPool {

    private BlockingQueue<Runnable> tasks;
    private List<Thread> threads;
    private boolean isShutDown;

    /**
     * @param size число потоков в пуле
     */

    private CustomThreadPool(int size) {
        this.tasks = new LinkedBlockingDeque<>();
        this.threads = new ArrayList<>(size);
        this.isShutDown = false;

        for (int i = 0; i < size; i++) {
            Executor w = new Executor(this);
            w.setName("Worker " + i);
            w.start();
            threads.add(w);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        CustomThreadPool p = new CustomThreadPool(5);
        for (int i = 0; i < 100; i++) {
            p.submit(new Thread("Thread " + i));
        }
        p.shutdown();
    }

    /**
     * Добавляет задачу в очередь пула
     *
     * @param task задача на выполнение
     * @throws InterruptedException
     */
    private void submit(Runnable task) throws InterruptedException {
        if (!this.isShutDown) {
            tasks.put(task);
        } else {
            throw new InterruptedException("Thread Pool shutdown is initiated, unable to submit task_6");
        }
    }

    /**
     * Выключение тред пула. Пул не принимает новые задачи, но те, что уже есть в очереди
     */
    private void shutdown() {
        isShutDown = true;
    }

    /**
     * Воркер - тред выполняющий задачи из очереди
     */
    private class Executor extends Thread {
        private CustomThreadPool pool;

        /**
         * Конструктор, привязывает поток к пулу.
         *
         * @param pool тред пул
         */
        Executor(CustomThreadPool pool) {
            this.pool = pool;
        }


        /**
         * Пока пул не выключен, получает задачи из очереди и выполняет их.
         */
        @Override
        public void run() {
            while (!pool.isShutDown || !this.pool.tasks.isEmpty()) {
                Runnable task;
                while ((task = this.pool.tasks.poll()) != null) {
                    task.run();
                    System.out.println(
                            "В пуле потоков : " + threads.size() +
                                    "\tОсталось задач : " + pool.tasks.size());
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Проблема с слипами...");
                    e.printStackTrace();
                }
            }
        }
    }

}