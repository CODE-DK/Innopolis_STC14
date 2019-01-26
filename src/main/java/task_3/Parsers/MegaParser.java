package task_3.Parsers;

import java.io.IOException;

/**
 * Интерфейс, описывает логику парсера
 * @author Komovskiy Dmitriy
 * @version 18.01.2019 v1.0
 */
public interface MegaParser {

    /**
     * метод для парсинга по ссылке
     * @param url входная строка тип {java.lang.String}
     * @return  массив распарсеных строк
     * @throws IOException если парсинт не удался или строка не является url ссылкой
     */
    String[] parse(String url) throws IOException;

    /**
     * метод для получения строкового представления html страницы
     * по url ссылке
     *
     * @param url входная строка, должна содержать url ссылку
     * @return  строку прочитанной по url html страницы
     * @throws IOException если чтение не удалось или переданая строка не являлется ссылкой
     */
    String getHtml(String url) throws IOException;
}
