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

    public SWriter(String path, Queue<String> queue) throws IOException {
        this.writerToFile = new FileWriter(path);
        this.queue = queue;
    }

    /**
     * метод для записи в файл
     * принимает на вход строку и пишет ее в файл
     */
    private void writeStringToFile() throws IOException {
        LOGGER.debug("начали запись");
        while (!isInterrupted()) {
            LOGGER.debug("пока не прерваны");
            if (!queue.isEmpty()) {
                LOGGER.debug("очередь не пустая");
                try {
                    LOGGER.debug("пишим в файл");
                    writerToFile.write(queue.poll() + "\n");
                    writerToFile.flush();
                } catch (IOException e) {
                    LOGGER.debug("Ошибка записи в файл", e);
                }
            }LOGGER.debug("очередь пустая");
        }LOGGER.debug("закрываем поток на запись");
        writerToFile.close();
    }

    @Override
    public void run() {
        try {
            writeStringToFile();
        } catch (IOException e) {
            LOGGER.debug("Ошибка при закрытии потока для записи", e);
            throw new RuntimeException(e);
        }
    }
}
