package task_9.IO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.mockito.Matchers.anyString;

public class SWriterTest {

    private SWriter writer;
    private FileWriter fileWriter;
    private Queue<String> queue;

    @Before
    public void init(){
        fileWriter = Mockito.mock(FileWriter.class);
        queue = new LinkedBlockingDeque<>();
        writer = new SWriter(fileWriter, queue);
    }

    @Test
    public void runTest() throws IOException {
        queue.add("test");
        writer.start();
        Mockito.verify(fileWriter, Mockito.times(1)).write(anyString());
        Mockito.verify(fileWriter, Mockito.times(1)).flush();
    }

    @Test
    public void interruptTest() throws IOException {
        queue.add("test");
        writer.start();
        writer.interrupt();
        Mockito.verify(fileWriter, Mockito.times(1)).close();
    }

    @Test
    public void writeStringToFileNegativeTest() throws IOException {
        fileWriter = Mockito.mock(FileWriter.class);
        queue = new LinkedBlockingDeque<>();
        writer = new SWriter(fileWriter, queue);
        queue.add("test");
        writer.start();
        Mockito.doThrow(IOException.class);
    }

    @Test
    public void closePositiveTest() throws IOException {
        writer.close();
        Mockito.verify(fileWriter, Mockito.times(1)).close();
    }

    @Test
    public void closeNegativeTest() {
        writer.close();
        Mockito.doThrow(IOException.class);
    }
}