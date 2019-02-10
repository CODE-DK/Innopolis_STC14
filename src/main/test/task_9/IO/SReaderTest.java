package task_9.IO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.fail;

public class SReaderTest {

    private static final String EMPTY = "";
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test(expected = RuntimeException.class)
    public void runFail() {
        new SReader(EMPTY
                , new String[0]
                , new LinkedBlockingQueue<>()).run();
    }

    @Test
    public void parseTest() throws IOException, InterruptedException {
        String test =
                "This is a long tense. " +
                        "Creates checking the parse method. " +
                        "It needs be sure that every thing is working correct." +
                        "Some other tense were added." +
                        "Let's try to parse!";
        File file = testFolder.newFile("forTestFile.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(test);
            writer.flush();
        }
        String[] words = {"parse"};
        Queue<String> queue = new LinkedBlockingQueue<>();
        new SReader(file.getAbsolutePath(), words, queue).start();
        Thread.sleep(1000);

        if (!"Creates checking the parse method.".equals(queue.poll())) {
            fail();
        }
        if (!"Let's try to parse!".equals(queue.poll())) {
            fail();
        }
    }
}