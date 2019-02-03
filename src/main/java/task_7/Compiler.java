package task_7;

import org.jetbrains.annotations.NotNull;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * класс отвечает за компиляцию переданного введенного текста
 * для реализации метода во внешний файл.
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
class Compiler {

    /**
     * путь к директории для компиляции
     */
    private final String dir = "target";
    private final String name;
    private StringBuilder builder;

    Compiler(String name) {
        builder = new StringBuilder();
        this.name = name;
    }

    /**
     * основной метод для компиляции
     */
    void compile() {
        String code = buildMethodImpl();
        javac(code);
    }

    /**
     * метод создает файл с расширением .java
     * далее компилируя в файл стандартными механизвами JVM
     *
     * @param code код для компиляции
     */
    private void javac(String code) {
        try {
            Files.write(Paths.get(getPath()), code.getBytes());
            JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
            String[] javacOpts = {getPath()};
            javac.run(null, null, null, javacOpts);
        } catch (IOException e) {
            System.out.println("Ошибка компиляции " + e);
        }
    }

    /**
     * метод возвращает путь для компиляции файла
     * с расширением .java
     *
     * @return строка - путь к файлу .java
     */
    @NotNull
    private String getPath() {
        return "./" + dir + "/" + name + ".java";
    }

    /**
     * метод строит базовую шапку файла
     * реализуя все тело класса в соответствии с интерфейсом Worker
     *
     * @return строку - тело класса для записи в файл и последующей компиляции
     */
    @NotNull
    private String buildMethodImpl() {
        builder.append(workerImpl());

        enterCode();
        builder.append("\n}\n}");

        System.out.println("ожидайте...");
        return builder.toString();
    }

    /**
     * читает код с клавиатуры, добавляет в строку, для последующей компиляции
     */
    private void enterCode() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("введите код...\nпо окончанию нажмите Enter");

            String line;
            while (!"".equals(line = scanner.nextLine())) {
                builder.append(line);
            }
        }
    }

    /**
     * оголовная заготовка тела класса
     * @return строка - класс
     */
    private String workerImpl() {
        return /*"package target;\n" +*/
                "import task_7.Worker;\n" +
                "public class SomeClass implements Worker {\n" +
                "public void doWork(){\n";
    }
}

