package task_6;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * класс предназначен для сериализации объектов
 * поля которых являются простыми типами и
 * помеченных аннотацией {@code XmlSerialize}
 */
class XmlOutputStream {

    private StringBuilder builder;

    XmlOutputStream() {
        this.builder = new StringBuilder();
    }

    /**
     * читаем поля объекта в строку
     *
     * @param o входной объект
     * @return строкое представление объекта
     * @throws IllegalAccessException если доступ к полям
     *                                класса не удался
     */
    private String readObject(Object o) throws IllegalAccessException {

        if (!o.getClass().isAnnotationPresent(XmlSerialize.class)) {
            System.out.println("невозможно сериализовать данный объект");
            return null;
        }
        begin(o.getClass().getName(), null, null);
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            addLine(o, field);
        }
        end(o);
        return builder.toString();
    }

    /**
     * метод создает открывающий тэг заголовка
     * и добавляет в выходную строку {code StringBuilder}
     *
     * @param name  имя поля
     * @param type  тип переменной
     * @param value значение переменной
     */
    private void begin(String name, String type, String value) {
        builder.append(wrap(name, type, value));
    }

    /**
     * метод создает закрывающий тэг заголовка
     * и добавляет в выходную строку {code StringBuilder}
     *
     * @param o объект для которого создается обертка
     */
    private void end(Object o) {
        builder.append(wrap(o.getClass().getName(), null, null)
                .replaceAll("<", "</"));
    }

    /**
     * для каждого поля объекта создается строковое представление в формате
     * имя + тип + значение и добавляет в выходную строку {code StringBuilder}
     *
     * @param o     объект, поле которого оборачивается
     * @param field поле объекта
     * @throws IllegalAccessException если доступ к закрытому полю не открыт
     */
    private void addLine(Object o, Field field) throws IllegalAccessException {
        field.setAccessible(true);

        String name = field.getName();
        String type = field.getType().getSimpleName();
        String value = field.get(o).toString();

        begin(name, type, value);
    }

    /**
     * метод записыват строку в формате xml в выходной файл
     *
     * @param o объект, который нужно серилизовать
     * @throws IllegalAccessException при ошибке доступа к полям
     *                                сериализуемого класса
     * @throws IOException            при ошибке чтения/записи
     */
    void serialize(Object o, String file) throws IllegalAccessException, IOException {
        Files.write(Paths.get(file), Objects.requireNonNull(readObject(o)).getBytes());
    }

    /**
     * оборачивает строку тэгами формата xml.
     * результат вида : <имя тип> значение </имя>
     *
     * @param name  имя переменной
     * @param type  тип переменной
     * @param value значение
     * @return строку в формате xml вида
     * <имя тип> значение </имя>
     */
    private String wrap(String name, String type, String value) {
        if (type == null) {
            return "<" + name + ">" + "\n";
        }
        return "<" + name + " " + "type=\"" + type + "\">" +
                value + "</" + name + ">" + "\n";
    }
}