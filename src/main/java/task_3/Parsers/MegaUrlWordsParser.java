package task_3.Parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс реализует парсинг по url ссылке, реализует интерфейс {@code MegaParser}
 *
 * @author Komovskiy Dmitriy
 * @version 18.01.2019 v1.0
 */
public class MegaUrlWordsParser implements MegaParser {

    /**
     * Внутренняя структура данных для хранения уникальных слов по url в латинице
     */
    private Set<String> wordbook;

    /**
     * буффер, ограничивает максимальное количество найденных слов по url в латинице
     */
    private static final int SIZE = 1000; // количество слов на выходе

    /**
     * Конструктор инициализации класса, задает структуру данных хранилища
     */
    public MegaUrlWordsParser() {
        this.wordbook = new HashSet<>();
    }

    /**
     * Внутренний метод парсинга html текста
     * Использует регулярное выражение, искомые слова содержат 1-15 символов
     *
     * @param html строка которую хотим парсить
     * @throws IOException в случае если регулярное выражение указано не верно
     */
    private void doParse(String html) {

        Pattern pattern = Pattern.compile("[a-zA-Z]{1,15}"); // слова в латинице длиной не более 15 символов
        Matcher matcher;
        matcher = pattern.matcher(html);

        int counter = 0;
        while (matcher.find() && counter < SIZE) { // формируем список SIZE уникальных слов
            wordbook.add(matcher.group().toLowerCase());
            counter = wordbook.size();
        }
    }

    /**
     * Внутренняя функция копирования в массив строк
     *
     * @param set строк после парсинга
     * @return массив строк, являющийся копией строк хранилища
     */
    private String[] toArray(Set<String> set) {
        return set.toArray(new String[0]);
    }

    /**
     * Основной метод класса, обеспечивает парсинг по url
     * выходная структура данных - массив уникальных строк
     *
     * @param url ссылка
     * @return массив в случае успешного парсинга
     * @throws IOException прокидывается из getHtml метода
     */
    @Override
    public String[] parse(String url) throws IOException {
        String html = getHtml(url);
        doParse(html);
        if (wordbook != null){
            return toArray(wordbook);
        }
        return new String[0];
    }

    /**
     * Внутренний метод для получения html строки по url
     * используется внешняя открытая библиотека Jsoup
     *
     * @param url ссылка
     * @return строковое представление html страниц
     * @throws IOException если парсинг страницы не получился.
     *                     Исключение генерирует внутренний метод библиотеки
     */

    private String getHtml(String url) throws IOException {

        Document doc = Jsoup.connect(url).get(); // библиотека JSOUP является сторонней библиотекой, распрастраняется в свободном доступе и упрощает работу с html страницами
        return doc.html();
    }
}
