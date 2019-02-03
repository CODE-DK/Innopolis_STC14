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
            "./target/data/in.txt",
            "./target/data/in2.txt",
            "./target/data/in3.txt",
            "./target/data/in4.txt",
    };

    /**
     * массив слов для поиска совпадений
     */
    static final String[] WORDS = {
            "книга",
            "того",
            "будем"
    };

    /**
     * путь к выходному файлу
     */
    static final String RES = "./target/data/out.txt";
}
