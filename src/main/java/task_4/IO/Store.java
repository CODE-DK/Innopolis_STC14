package task_4.IO;

/**
 * класс предназначен как хранилище входных данных
 * предназначен для тестирования классов MultiReader & SingleWriter
 */
public class Store {

    /**
     * массив ссылок на ресурсы
     */
    private final String[] sources = {
            "https://baconipsum.com/api/?type=all-meat&paras=3&start-with-lorem=1&format=html",
            "https://baconipsum.com/api/?type=meat-and-filler&paras=5&format=text",
            "http://lib.ru/ADAMS/rhit1.txt",
            "http://lib.ru/ADAMS/hitch_3.txt",
            "http://lib.ru/ADAMS/hitch_3_sp.txt",
            "http://lit.lib.ru/k/kazin_a_l/karamazovy.shtml",
            "./stc14/src/main/java/task_4/data/text_0.txt",
            "./stc14/src/main/java/task_4/data/text_1.txt",
            "./stc14/src/main/java/task_4/data/text_2.txt"
    };

    /**
     * массив слов для поиска совпадений
     */
    private final String[] words = {
            "practice",
            "theory",
            "практика",
            "теория"
    };

    /**
     * путь к выходному файлу
     */
    private final String res = "./stc14/src/main/java/task_4/data/out.txt";

    /**
     * метод для получения массива ссылок
     * @return массив ссылок {@code links}
     */
    public String[] getSources() {
        return sources;
    }

    /**
     *  метод для получения массива слов
     * в виде множества
     * @return множество слов {@code words}
     */
    public String[] getWords() {
        return words;
    }

    /**
     * возвращает путь к файлу для записи
     * @return путь к файлу в виде строки
     */
    public String getRes() {
        return res;
    }

}
