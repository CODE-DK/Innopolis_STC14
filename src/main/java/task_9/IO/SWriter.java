package task_9.IO;

import java.io.FileWriter;
import java.io.IOException;

/**
 * класс для записи в выходной файл
 * предназначен для многопоточной работы с несколькими
 * источниками
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class SWriter {

    private FileWriter writerToFile;

    public SWriter(String pathToFile) throws IOException {
        this.writerToFile = new FileWriter(pathToFile);
    }

    /**
     * метод для записи в файл
     * принимает на вход строку и пишет ее в файл
     *
     * @param sentence входная строка
     * @throws IOException если при записи что то пошло не так
     */
    synchronized void writeStringToFile(String sentence) {
        try {
            writerToFile.write(sentence + "\n");
            writerToFile.flush();
        } catch (IOException e) {
            System.out.println("e = " + e);
        }
    }
}
