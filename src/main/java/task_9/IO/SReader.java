package task_9.IO;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
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

    private static final String REG_EX = "[A-ZА-Я].+?[.|!|?]";
    private BufferedReader reader;
    private final HashSet words;

    /**
     * поле для записи в выходной файл
     */
    private final SWriter sWriter;
    private final StringBuilder builder;

    public SReader(String file, SWriter s, HashSet<String> words) throws IOException {
        this.sWriter = s;
        initReader(file);
        this.words = words;
        this.builder = new StringBuilder();
    }

    /**
     * по переданой строке инициализируем поле {@code reader}
     *
     * @param file строка - путь к файлу либо url
     * @throws FileNotFoundException если файл не существует или доступ к ссылке закрыт
     */
    private void initReader(String file) throws FileNotFoundException {
        try {
            URL url = new URL(file);
            this.reader = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception e) {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }
    }

    /**
     * метод читает файл по по строкам в буффер
     */
    private void parseText() {
        reader.lines()
                .map(builder::append)
                .forEach(x -> findRegEx());
    }

    /**
     * метод сравнивает переданною строку на наличие в нем оконченного предложения
     * и если находит, удаляет его из буффера
     */
    private void findRegEx() {
        Matcher matcher = Pattern.compile(REG_EX).matcher(builder.toString());
        while (matcher.find()) {
            String res = matcher.group();
            findSentence(res);
            builder.delete(0, res.length());
        }
    }

    /**
     * метод сравнивает переданную строку на вхождение слов из
     * {code words} и если находит, передает строку на запись
     *
     * @param s строка по которой осуществляется поиск
     */
    private void findSentence(String s) {
        HashSet<String> sentence = parseSentence(s);
        if (!Collections.disjoint(words, sentence)) {
            sWriter.writeStringToFile(s);
        }
    }

    /**
     * метод разбивает переданную строку на set слов
     * переводя в нижний регистр
     *
     * @param sentence входная строка - предложение
     * @return множество слов из строки
     */
    private HashSet<String> parseSentence(String sentence) {
        HashSet<String> result = new HashSet<>();
        Collections.addAll(result, removePunctuation(sentence.toLowerCase()).split("\\s+"));
        return result;
    }

    /**
     * метод убирает знаки пунктуации из переданной строки
     *
     * @param string входная строка - предложение
     * @return строка без знаков пунктуации
     */
    private String removePunctuation(String string) {
        return string.replaceAll("[^a-zA-Zа-яА-Я\\d\\s]", "");
    }

    @Override
    public void run() {
        parseText();
    }
}
