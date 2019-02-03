package task_9.IO;

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

    private FileWriter writerToFile;
    private Queue<String> queue;

    public SWriter(String pathToFile, Queue<String> queue) {
        try {
            this.writerToFile = new FileWriter(pathToFile);
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу для записи = " + e);
        }
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
                    System.out.println("Ошибка записи в файл = " + e);
                }
            }
        }
    }

    @Override
    public void run() {
        writeStringToFile();
    }
}
