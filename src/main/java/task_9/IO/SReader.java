package task_9.IO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
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

    private final String[] words;
    private final String file;
    private Queue<String> queue;

    public SReader(String file, String[] words, Queue<String> queue) {
        this.words = words;
        this.file = file;
        this.queue = queue;
    }

     Queue<String> getQueue() {
        return queue;
    }

     void setQueue(Queue<String> queue) {
        this.queue = queue;
    }

    /**
     * по переданой строке инициализируем поле {@code reader}
     *
     * @param file строка - путь к файлу либо url
     * @throws FileNotFoundException если файл не существует или доступ к ссылке закрыт
     */
     BufferedReader initReader(String file) throws FileNotFoundException {
        try {
            URL url = new URL(file);
            return new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception e) {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }
    }

    /**
     * метод читает файл по по строкам в буффер
     */
     void parseText(BufferedReader reader) {
        final StringBuilder builder = new StringBuilder();
        reader.lines()
                .map(builder::append)
                .forEach(this::findRegEx);
    }

    /**
     * метод сравнивает переданною строку на наличие в нем оконченного предложения
     * и если находит, удаляет его из буффера
     */
     void findRegEx(StringBuilder builder) {
        final Matcher matcher = Pattern.compile(REG_EX).matcher(builder.toString());
        while (matcher.find()) {
            String result = matcher.group();
            if (findSentence(result, words)){
                addToQueue(result);
            }
            builder.delete(0, result.length());
        }
    }

    /**
     * метод сравнивает переданную строку на вхождение слов из
     * {code words.txt} и если находит, передает строку на запись
     *
     * @param string строка по которой осуществляется поиск
     */
     boolean findSentence(String string, String[] words) {
        return !Collections.disjoint(Arrays.asList(words), Arrays.asList(parseSentence(string)));
    }

     void addToQueue(String sentence){
        queue.add(sentence);
    }

    /**
     * метод разбивает переданную строку на set слов
     * переводя в нижний регистр
     *
     * @param sentence входная строка - предложение
     * @return множество слов из строки
     */
     String[] parseSentence(String sentence) {
        return removePunctuation(sentence.toLowerCase()).split(SPACE);
    }

    /**
     * метод убирает знаки пунктуации из переданной строки
     *
     * @param string входная строка - предложение
     * @return строка без знаков пунктуации
     */
     String removePunctuation(String string) {
        return string.replaceAll(PUNCTUATIONS, "");
    }

    @Override
    public void run() {
        try {
            parseText(initReader(file));
        } catch (FileNotFoundException e) {
            LOGGER.debug("Ошибка доступа к файлу в потоке чтения = {}", e);
        }
    }
}
