package task_6;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * класс предназначен для сериализации классов
 * содержащих в полях простые типы данных и
 * анотированных {code @XmlSerialize}
 */
class XmlInputStream {

    private static final String GET = "[A-Za-z_\\w.]++|\\w+";

    /**
     * метод для чтения объекта из xml файла
     *
     * @return объект после десериализации
     * @throws IOException если при чтении из файла не удалось
     */
    Object deSerialize(String xml) throws IOException {

        byte[] file = Files.readAllBytes(Paths.get(xml));
        String out = new String(file);
        LinkedHashSet<String> set = find(out);
        return getObject(set);
    }

    /**
     * метод формирует объект на основе переданных
     * ему полей
     *
     * @param set множество полей объекта
     * @return объект класса, инициализированный полями
     * из множества
     */
    private Object getObject(LinkedHashSet<String> set) {

        Iterator i = set.iterator();
        Object o = createClass(i.next().toString());

        while (i.hasNext()) {

            String name = (i.next().toString());
            String type = (i.next().toString());
            String value = (i.next().toString());

            try {
                castField(Objects.requireNonNull(getField(o, name)), o, type, value);
            } catch (IllegalAccessException e) {
                System.out.println("каст к полям не удачный...");
            }
        }
        return o;
    }

    /**
     * вернет поле у переданного объекта по имени
     *
     * @param o    объект
     * @param name имя поля
     * @return поле объекта по имени или null если
     * такого поля не существует
     */
    private Field getField(Object o, String name) {
        try {
            return o.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * создает класс по переданному имени
     * если создание класса будет не удачным
     * вернет ""Fail with classLoader, can't create newInstance()"
     * или "Class not found" при нестыковке имен
     *
     * @param name полное имя класса
     * @return объект класса.
     */
    private Object createClass(String name) {
        try {
            Class clazz = Class.forName(name);
            Object o = clazz.getConstructor().newInstance();
            return o;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (IllegalAccessException | InstantiationException |
                NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Fail with classLoader, can't create newInstance()");
        }
        return null;
    }

    /**
     * парсим строку xml выделяя поля, типы данных и значения
     *
     * @param line строка - объект формата xml
     * @return множество значений по строке
     */
    private LinkedHashSet<String> find(String line) {

        Pattern pattern = Pattern.compile(XmlInputStream.GET);
        Matcher matcher = pattern.matcher(line);

        LinkedHashSet<String> set = new LinkedHashSet<>();
        String local;
        while (matcher.find()) {
            local = matcher.group();
            if ("type".equals(local)) continue;
            set.add(matcher.group());
        }
        return set;
    }

    /**
     * кастим простые типы, устанавливаем поля объекта
     *
     * @param field поле объекта
     * @param o     объект
     * @param type  тип переменной
     * @param value значение переменной
     * @throws IllegalAccessException
     */
    private void castField(Field field, Object o, String type, String value) throws IllegalAccessException {

        field.setAccessible(true);

        if ("int".equals(type)) {
            field.setInt(o, Integer.parseInt(value));

        } else if ("short".equals(type)) {
            field.setShort(o, Short.parseShort(value));

        } else if ("byte".equals(type)) {
            field.setByte(o, Byte.parseByte(value));

        } else if ("long".equals(type)) {
            field.setLong(o, Long.parseLong(value));

        } else if ("double".equals(type)) {
            field.setDouble(o, Double.parseDouble(value));

        } else if ("float".equals(type)) {
            field.setFloat(o, Float.parseFloat(value));

        } else if ("boolean".equals(type)) {
            field.setBoolean(o, Boolean.parseBoolean(value));

        } else {
            field.set(o, value);
        }
    }
}

