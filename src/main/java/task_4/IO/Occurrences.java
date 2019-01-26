package task_4.IO;

import java.io.IOException;

/**
 * интерфейс в соответствии с заданием
 */
public interface Occurrences {

    /**
     * метод отвечае  за поиск по ресурсам из source
     * сравнивая на вхожения предложений в words
     * и в случае если находит вхождение, добавляет предложение в файл
     * путь к выходному файлу - res
     * @param sources массив ссылок на внешнии источники
     * @param words массив слов для проверки на вхождение в предложения
     * @param res путь к выходнму файлу для записи предложений
     */
    void getOccurrences(String[] sources, String[] words, String res) throws IOException;
}
