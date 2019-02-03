package task_7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * класс реализует логику кастомного загрузчика классов
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class CompileLoader extends ClassLoader {

    /**
     * метод отвечает за загрузку класса по переданному имени
     * в случае, если не нашел, вернет стандартную реализацию
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        if(TestClass.getClassName().equals(name)){
            try {
                byte[] bites = Files.readAllBytes(Paths.get("./target/SomeClass.class"));
                return defineClass(name, bites, 0, bites.length);
            } catch (IOException e) {
                System.out.println("IOException in findClass() of CompileLoader");
            }
        }
        return super.findClass(name);
    }
}