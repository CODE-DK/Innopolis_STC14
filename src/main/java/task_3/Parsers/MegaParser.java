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

}
