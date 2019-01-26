package task_4.IO;

import java.io.*;

/**
 * класс для записи в выходной файл
 * предназначен для многопоточной работы с несколькими
 * источниками
 */

public class SingleWriter implements AutoCloseable{

    /**
     * поток для записи в файл
     */
    private BufferedWriter bw;

    /**
     * конструктор
     * принимает путь к файлу, в который предполагается запись
     * @param path путь к файлу для записи
     * @throws IOException если запись в файл не удалась
     */
    public SingleWriter(String path) throws IOException {
        this.bw = new BufferedWriter(new FileWriter(path));
    }

    /**
     * метод для записи в файл
     * принимает на вход строку и пишет ее в файл
     * @param sentence входная строка
     * @throws IOException если при записи что то пошло не так
     */
    public synchronized void write(String sentence) throws IOException {
        this.bw.write(sentence + "\n");
    }

    /**
     * метод для закрытия потока для записи
     * @throws Exception если при закрытии потока что то пошло не так
     */
    @Override
    public void close() throws Exception {
        this.bw.close();
    }
}

