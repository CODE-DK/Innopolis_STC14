package task_6;

import java.io.IOException;

/**
 * тестовый класс
 */
public class TestClass {

    private static final String FILE = "./src/main/java/task_6/out/store.xml";

    public static void main(String[] args) throws IOException, IllegalAccessException {

        //создали юнита
        Unit before = new Unit(1, "John", 19216801L);

        //сериализовали в файл
        XmlOutputStream xos = new XmlOutputStream();
        xos.serialize(before, FILE);

        //десериализовали из файла
        XmlInputStream xis = new XmlInputStream();
        Unit after = (Unit) xis.deSerialize(FILE);

        //сравнили результат
        System.out.println("до : " + before);
        System.out.println("после : " + after);
    }
}

