package task_9.IO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;

/**
 * класс для записи в выходной файл
 * предназначен для многопоточной работы с несколькими
 * источниками
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class SWriter extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(SWriter.class);
    private FileWriter writerToFile;
    private Queue<String> queue;

    public SWriter(FileWriter fileWriter, Queue<String> queue) {
        this.writerToFile = fileWriter;
        this.queue = queue;
    }

    /**
     * метод для записи в файл
     * принимает на вход строку и пишет ее в файл
     */
    private void writeStringToFile() {
        while (!isInterrupted()) {
            if (!queue.isEmpty()) {
                try {
                    writerToFile.write(queue.poll() + "\n");
                    writerToFile.flush();
                } catch (IOException e) {
                    LOGGER.debug("Ошибка записи в файл", e);
                }
            }
        }
        close();
    }

    /**
     * закрытие потока для чтения
     */
    void close() {
        try {
            writerToFile.close();
        } catch (IOException e) {
            LOGGER.debug("Ошибка при закрытии потока для записи = {}", e);
        }
    }

    @Override
    public void run() {
        writeStringToFile();
    }
}
