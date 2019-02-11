package task_9.IO;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;

public class SWriterTest {

    private static final String EMPTY = "";
    private SWriter writer;
    private Queue<String> queue;
    private File file;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        queue = new LinkedBlockingQueue<>();
        file = testFolder.newFile("test.txt");
        writer = new SWriter(file.getAbsolutePath(), queue);
    }

    @Test
    public void runTest() throws IOException, InterruptedException {
        queue.add("one");
        queue.add("two");
        queue.add("three");
        String buffer = queue.toString();
        writer.start();
        while (!queue.isEmpty()){
            continue;
        }
        writer.interrupt();
        String result = Files.readAllLines(file.toPath()).toString();
        assertEquals(buffer, result);
    }

    @Test (expected = IOException.class)
    public void wrongPathToFile() throws IOException {
        new SWriter(EMPTY, queue);
    }
}