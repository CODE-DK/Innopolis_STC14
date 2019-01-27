package task_9;

/**
 * класс предназначен как хранилище входных данных
 * предназначен для тестирования классов MultiReader & SingleWriter
 */
class Store {

    /**
     * массив ссылок на ресурсы
     */
    static final String[] SOURCES = {
            "https://baconipsum.com/api/?type=all-meat&paras=3&start-with-lorem=1&format=html",
            "https://baconipsum.com/api/?type=meat-and-filler&paras=5&format=text",
            "./src/main/java/task_9/data/in.txt"
    };

    /**
     * массив слов для поиска совпадений
     */
    static final String[] WORDS = {
            "leberkas",
            "того",
            "hamburger"
    };

    /**
     * путь к выходному файлу
     */
    static final String RES = "./src/main/java/task_9/data/out.txt";
}
