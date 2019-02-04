package task_9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * класс загрузчик предназначен для загрузки
 *
 */
class LoaderImpl implements Loader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);
    private static final String PROPERTIES = "./target/data/loader.properties";
    private static final String SOURCES;
    private static final String WORDS;
    private static final String OUT_DIR;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(PROPERTIES));
        } catch (IOException e) {
            LOGGER.info("Ошибка при чтении файла .properties - ", e);
        }
        SOURCES = properties.getProperty("source");
        WORDS = properties.getProperty("words");
        OUT_DIR = properties.getProperty("dir");
    }

    @Override
    public String[] loadSources() {
        try {
            List<String> array = Files.readAllLines(Paths.get(SOURCES));
            System.out.println(array);
            return array.toArray(new String[0]);
        } catch (IOException e) {
            LOGGER.info("Ошибка при чтении файла ресурсов - ", e);
        }
        return new String[0];
    }

    public String[] loadWords() {
        try {
            List<String> array = Files.readAllLines(Paths.get(WORDS));
            System.out.println(array);
            return array.toArray(new String[0]);
        } catch (IOException e) {
            LOGGER.info("Ошибка при чтении массива слов - ", e);
        }
        return new String[0];
    }

    public String getOutDir() {
        System.out.println(OUT_DIR);
        return OUT_DIR;
    }
}
