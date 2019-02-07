package task_5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * класс реализует пул потоков выполнения,
 * управляет жизненным циклом потоков,
 * использует внутренний класс - исполнитель
 *
 * @author Комовский Дмитрий
 * @version v1.0
 */
class CustomThreadPool {

    private final BlockingQueue<Runnable> tasks;
    private final List<Thread> threads;
    private boolean isShutDown;

    CustomThreadPool(int size) {

        this.tasks = new LinkedBlockingDeque<>();
        this.threads = new ArrayList<>(size);
        this.isShutDown = false;

        for (int i = 0; i < size; i++) {
            Executor executor = new Executor(this);
            executor.start();
            threads.add(executor);
        }
    }

    /**
     * @return метод возвращает текущее количество
     * задач, добавленых в пул
     */
    BlockingQueue<Runnable> getTasks() {
        return tasks;
    }

    /**
     * @return метод возвращает текущее количество
     * работающих потоков исполнения
     */
    int numberOfALiveThreads() {
        return threads.size();
    }

    /**
     * флаг закрытия пула,
     * все задачи добавленые в пул до вызова метода будут выполнены
     * новые задачи более не доступны для добавления
     *
     * @return true тогда и только тогда
     * когда ранее был вызван метод shutdown()
     */
    boolean isShutDown() {
        return isShutDown;
    }

    /**
     * Добавляет задачу в очередь пула потоков
     * если пул закрыт для добавления (ранее был вызван метод shutdown())
     * метод выбрасывает ошибку о том, что пул более не доступен для добавления задач
     *
     * @param task новая задача для выполнения
     * @throws InterruptedException если пул закрыт для новых задач
     */
    void submit(Runnable task) throws InterruptedException {
        if (!this.isShutDown) {
            tasks.put(task);
        } else {
            throw new InterruptedException("Thread Pool shutdown is initiated, unable to submit task_6");
        }
    }

    /**
     * Закрытие пула. Пул не принимает новые задачи, но те,
     * что уже есть в очереди будут выполнены
     */
    void shutdown() {
        isShutDown = true;
    }

    /**
     * внутренний класс выполняющий задачи из очереди
     * берет задачу из списка имеющихся в пуле и выполняет ее
     */
    private class Executor extends Thread {

        private final CustomThreadPool pool;

        private Executor(CustomThreadPool pool) {
            this.pool = pool;
        }

        /**
         * пока есть задачи в очереди и пул не закрыт
         * метод выполняет задачи из очереди
         */
        @Override
        public void run() {
            while (!pool.isShutDown() || !this.pool.getTasks().isEmpty()) {
                Runnable task;
                while ((task = this.pool.getTasks().poll()) != null) {
                    task.run();
                }
            }
        }
    }
}