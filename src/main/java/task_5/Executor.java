package task_5;

/**
 * Воркер - тред выполняющий задачи из очереди
 */
class Executor extends Thread {

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
        while (!pool.isShutDown() || !this.pool.getTasks().isEmpty()) {
            Runnable task;
            while ((task = this.pool.getTasks().poll()) != null) {
                task.run();
                System.out.println(
                        "В пуле потоков : " + pool.getThreads().size() +
                                "\tОсталось задач : " + pool.getTasks().size());
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Проблема с слипами..." + e);
            }
        }
    }
}
