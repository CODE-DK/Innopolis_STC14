package task_5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Простой кастомный тред пул
 */
class CustomThreadPool {

    private final BlockingQueue<Runnable> tasks;
    private final List<Thread> threads;
    private boolean isShutDown;

    /**
     * @param size число потоков в пуле
     */

    CustomThreadPool(int size) {
        this.tasks = new LinkedBlockingDeque<>();
        this.threads = new ArrayList<>(size);
        this.isShutDown = false;

        for (int i = 0; i < size; i++) {
            Executor executor = new Executor(this);
            executor.setName("Worker " + i);
            executor.start();
            threads.add(executor);
        }
    }

    BlockingQueue<Runnable> getTasks() {
        return tasks;
    }

    List<Thread> getThreads() {
        return threads;
    }

    boolean isShutDown() {
        return isShutDown;
    }

    /**
     * Добавляет задачу в очередь пула
     *
     * @param task задача на выполнение
     * @throws InterruptedException
     */
    void submit(Runnable task) throws InterruptedException {
        if (!this.isShutDown) {
            tasks.put(task);
        } else {
            throw new InterruptedException("Thread Pool shutdown is initiated, unable to submit task_6");
        }
    }

    /**
     * Выключение тред пула. Пул не принимает новые задачи, но те, что уже есть в очереди
     */
    void shutdown() {
        isShutDown = true;
    }

}