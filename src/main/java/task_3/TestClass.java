package task_3;

import task_3.Builders.MegaFileBuilder;
import task_3.Parsers.MegaUrlWordsParser;

import java.io.IOException;

/**
 * тестовый класс основной метод getFiles()
 * на вход подается (pathToSomeDir, numberOfFiles, sizeInKBytes, probability)
 * который генерирует по заданнному пути n файлов размера size где размер
 * задается в килобайтах.
 */
public class TestClass {

    private static final String URL = "https://en.wikipedia.org/wiki/Java_(programming_language)";
    private static final String TARGET = "./stc14/src/main/java/task_3/out";

    public static void main(String[] args) throws IOException {

        MegaUrlWordsParser urlWordsParser = new MegaUrlWordsParser();
        String[] words = urlWordsParser.parse(URL);

        MegaFileBuilder mfb = new MegaFileBuilder();
        mfb.getFiles(TARGET, 10, 1000, words, 50);
    }
}
