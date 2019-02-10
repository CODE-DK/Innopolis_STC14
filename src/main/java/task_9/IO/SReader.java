package task_9.IO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * многопоточный класс для парсинга текста по входной ссылке.
 * В качестве ссылки может передаваться url ссылка или путь к файлу
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class SReader extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(SReader.class);
    private static final String REG_EX = "[A-ZА-Я].+?[.|!|?]";
    private static final String PUNCTUATIONS = "[^a-zA-Zа-яА-Я\\d\\s]";
    private static final String SPACE = "\\s+";
    private static final String EMPTY = "";

    private final String[] words;
    private final String file;
    private final Queue<String> queue;

    public SReader(String file, String[] words, Queue<String> queue) {
        this.words = words;
        this.file = file;
        this.queue = queue;
    }

    /**
     * по переданой строке инициализируем поле {@code reader}
     *
     * @throws IOException если файл не существует или доступ к ссылке закрыт
     */

    private InputStream getStream() throws IOException {
        LOGGER.debug("открытие стрима по ссылке");
        try {
            return new URL(file).openStream();
        } catch (MalformedURLException e) {
            return FileUtils.openInputStream(new File(file));
        }
    }

    /**
     * метод читает файл по по строкам в буффер
     */
    private void parseText() throws IOException {
        final StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getStream()))) {
            LOGGER.debug("начало парсинга");
            reader.lines()
                    .map(builder::append)
                    .forEach(this::findRegEx);
        } finally {
            LOGGER.debug("парсинг закончен");
        }
    }

    /**
     * метод сравнивает переданною строку на наличие в нем оконченного предложения
     * и если находит, удаляет его из буффера
     */
    private void findRegEx(StringBuilder builder) {
        final Matcher matcher = Pattern.compile(REG_EX).matcher(builder.toString());
        while (matcher.find()) {
            String result = matcher.group();
            LOGGER.debug("найдено совпадение {}", result);
            if (findSentence(result, words)) {
                addToQueue(result);
            }
            builder.delete(0, result.length());
            LOGGER.debug("в буффере осталось {}", builder);
        }
    }

    /**
     * метод сравнивает переданную строку на вхождение слов из
     * {code words.txt} и если находит, передает строку на запись
     *
     * @param string строка по которой осуществляется поиск
     */
    private boolean findSentence(String string, String[] words) {
        return !Collections.disjoint(Arrays.asList(words), Arrays.asList(parseSentence(string)));
    }

    private void addToQueue(String sentence) {
        queue.add(sentence);
    }

    /**
     * метод разбивает переданную строку на set слов
     * переводя в нижний регистр
     *
     * @param sentence входная строка - предложение
     * @return множество слов из строки
     */
    private String[] parseSentence(String sentence) {
        return removePunctuation(sentence.toLowerCase()).split(SPACE);
    }

    /**
     * метод убирает знаки пунктуации из переданной строки
     *
     * @param string входная строка - предложение
     * @return строка без знаков пунктуации
     */
    private String removePunctuation(String string) {
        return string.replaceAll(PUNCTUATIONS, EMPTY);
    }

    @Override
    public void run() {
        try {
            parseText();
        } catch (IOException e) {
            LOGGER.error("ошибка ввода вывода в run()", e);
            throw new RuntimeException(e);
        }
    }
}
