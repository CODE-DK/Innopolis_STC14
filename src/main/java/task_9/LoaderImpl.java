package task_9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * класс загрузчик предназначен для загрузки
 */
class LoaderImpl implements Loader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);
    private final String SOURCES;
    private final String WORDS;
    private final String OUT_DIR;

    LoaderImpl(Properties properties) {
        SOURCES = properties.getProperty("source");
        WORDS = properties.getProperty("words");
        OUT_DIR = properties.getProperty("dir");
    }

    @Override
    public String[] loadSources() {
        try {
            List<String> array = Files.readAllLines(Paths.get(SOURCES));
            return array.toArray(new String[0]);
        } catch (IOException e) {
            LOGGER.info("Ошибка при чтении файла ресурсов - ", e);
            return new String[0];
        }
    }

    @Override
    public String[] loadWords() {
        try {
            List<String> array = Files.readAllLines(Paths.get(WORDS));
            return array.toArray(new String[0]);
        } catch (IOException e) {
            LOGGER.info("Ошибка при чтении массива слов - ", e);
            return new String[0];
        }
    }

    @Override
    public String getOutDir() {
        return OUT_DIR;
    }
}
