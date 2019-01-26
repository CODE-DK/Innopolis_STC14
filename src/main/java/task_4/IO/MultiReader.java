package task_4.IO;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * многопоточный класс для парсинга текста по входной ссылке.
 * В качестве ссылки может передаваться url ссылка или путь к файлу
 */
public class MultiReader implements Runnable {

    /**
     * регулярные выражения для определения начала предложения,
     * конца и есть ли в строке предложение
     */

    private static final Pattern END = Pattern.compile("[A-ZА-Я][^!?.]+$");
    private static final Pattern BEGIN = Pattern.compile("^[^A-ZА-Я!?.]+[!?.]");
    private static final Pattern STRING = Pattern.compile("[A-ZА-Я][^!?.]+[!?.]", Pattern.MULTILINE);

    /**
     * буффер для формирования строки
     */
    private StringBuilder sb;

    private final Set<String> words;
    private final SingleWriter sw;
    private final String link;

    public MultiReader(String link, Set<String> words, SingleWriter sw) {
        this.words = words;
        this.sw = sw;
        this.link = link;
        sb = new StringBuilder(0);
    }

    /**
     * возвращает BufferedReader для чтения по ссылке {@code link}
     *
     * @return {@link java.io.BufferedReader} для ссылки {@code link}
     * @throws IOException если поток для чтения создать не удалось
     */
    private BufferedReader getStream() throws IOException {

        if (isURL(link)) {
            return new BufferedReader(new InputStreamReader(new URL(link).openStream()));
        } else {
            return new BufferedReader(new FileReader(link));
        }
    }

    /**
     * Основной метод класса, парсит строки по заданному правилу
     *
     * @throws IOException если при чтении что-то пошло не так
     */
    private void parse() throws IOException {

        try (BufferedReader reader = getStream()) {
            String content;
            while ((content = reader.readLine()) != null) {
                this.checkLine(content);
            }
        }
    }

    /**
     * метод формирует предложения по заданому правилу.
     * Предложение считается правильным только если оно
     * начинаться с [A-ZА-Я] строчной буквы и оканчиваться {.|!|?}.
     *
     * @param line входная строка для парсинга
     * @throws IOException если
     */
    private void checkLine(String line) throws IOException {
        if (findBegin(line)) return;

        buildSentence(line);

        findEnd(line);
    }

    /**
     * если слово есть в предложении, пишем предложение в выходной файл
     *
     * @param line входная строка для парсинга на предложения
     * @throws IOException если не удалась запись в файл
     */
    private void buildSentence(String line) throws IOException {
        for (String matched : findMatches(STRING, line)) {
            if (isIntersected(matched)) {
                this.sw.write(matched);
            }
        }
    }

    /**
     * Строим предложение из кусочков в StringBuilder и если удалось
     * записываем в выходной файл
     *
     * @param line входная строка для поиска начала предложения
     * @return true если начало предложения найден
     * @throws IOException если запись в файл не удалась
     */
    private boolean findBegin(String line) throws IOException {

        if (sb.capacity() != 0) {
            String[] continuations = findMatches(BEGIN, line);
            if (continuations.length == 0) {
                sb.append(line);
                return true;
            }

            String continuation = continuations[0];
            String sentence = sb.toString() + " " + continuation;
            if (isIntersected(sentence)) {
                this.sw.write(sentence);
            }
            sb.setLength(0);
        }
        return false;
    }

    /**
     * метод для поиска окончания предложения
     *
     * @param line входная строка для поиска окончания предложения
     */
    private void findEnd(String line) {
        String[] begin = findMatches(END, line);
        if (begin.length != 0) {
            sb.append(begin[0]);
        }
    }

    /**
     * метод сравнивает все слова из {@code words} на вхождение в {@code sentence}
     *
     * @param sentence входная строка - предложение
     * @return true если пересечения не найдено
     * иначе false
     */
    private boolean isIntersected(String sentence) {
        return !Collections.disjoint(parseSentence(sentence), words);
    }

    /**
     * метод формирует множество слов из входного предложения
     *
     * @param sentence входная строка
     * @return множество слов в строке без учета регистра
     */
    private Set<String> parseSentence(String sentence) {
        Set<String> words = new HashSet<>();
        Collections.addAll(words, removePunctuation(sentence.toLowerCase()).split("\\s+"));
        return words;
    }

    /**
     * метод возвращает массив вхождений подстрок в заданую строку
     * по паттерну (@ pattern)
     *
     * @param pattern правило выборки подстроки во входной строке
     * @param content входная строка
     * @return массив строк, найденых по заданному шаблону
     */
    private String[] findMatches(Pattern pattern, String content) {

        List<String> matches = new LinkedList<>();

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            matches.add(matcher.group(0));
        }
        return matches.toArray(new String[0]);
    }

    /**
     * метод убирет все знаки пунктуации, возвращая строку в нижнем регистре
     *
     * @param string входная строка
     * @return строку после обработки
     */
    private String removePunctuation(String string) {
        return string.replaceAll("[^a-zA-Zа-яА-Я\\d\\s]", "");
    }

    /**
     * проверяет, является ли входная строка URL ссылкой.
     * Если строка не является url ссылкой, она считается путем к файлу
     *
     * @param url входная проверяемая строка
     * @return true тогда и только тогда, когда строка является url ссылкой
     */
    private boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * метод запуска потока
     */
    @Override
    public void run() {
        try {
            parse();
        } catch (IOException e) {
            System.out.println("Парсинг не удался...");
            System.out.println(e);
        }
    }
}
