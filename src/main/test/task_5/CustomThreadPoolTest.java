package task_5;

import org.junit.Before;
import org.junit.Test;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

public class CustomThreadPoolTest {

    private CustomThreadPool pool;
    private final String EMPTY = "";

    @Before
    public void setUp() {
        pool = new CustomThreadPool(5);
    }

    @Test (expected = InterruptedException.class)
    public void shutDownTest() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            pool.submit(() -> System.out.println(EMPTY));
        }
        pool.shutdown();
        pool.submit(()-> System.out.println(1));
    }

    @Test
    public void poolTest() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            pool.submit(() -> System.out.println(EMPTY));
        }
        pool.shutdown();
        while (pool.getTasks().size() > 0){
            assertTrue(pool.numberOfALiveThreads() <= 5);
        }
    }

    @Test
    public void tasksTest() throws InterruptedException {
        final Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        final int taskNumber = 100;
        for (int i = 0; i < taskNumber; i++) {
            pool.submit(() -> queue.add(new Random().nextInt()));
        }
        pool.shutdown();
        while (pool.getTasks().size() > 0){
            continue;
        }
        assertEquals(queue.size(), taskNumber);
    }
}