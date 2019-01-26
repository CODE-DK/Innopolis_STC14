package task_7;

import java.lang.reflect.InvocationTargetException;

/**
 * test class
 */
public class TestClass {

    static final String SIMPLE_CLASS_NAME = "SomeClass";
    static final String PACKAGE_NAME = "task_7.out.SomeClass";

    public static void main(String[] args) {

        Compiler java = new Compiler();
        java.compile();

        ClassLoader cl = new CompileLoader();
        Object o = getObject(cl);
        doIt(o);
    }

    /**
     * метод - испольнитель
     * @param o объект действия
     */
    private static void doIt(Object o) {
        if (o instanceof Worker) {
            Worker w = (Worker) o;
            w.doWork();
        }
    }

    /**
     * метод слушит для создания экземпляра класса
     * пользуясь конструктором без параметров создаваемого класса
     *
     * @param cl класс - загрузчик
     * @return экземпляр требуемого класса в соответствии с используемым именем
     */
    private static Object getObject(ClassLoader cl) {
        try {

            Class<?> clazz = cl.loadClass(PACKAGE_NAME);
            return clazz.getConstructor().newInstance();

        } catch (IllegalAccessException e) {
            System.out.println("нет доступа к созданию экземпляра через classLoader");
        } catch (InstantiationException e) {
            System.out.println("classLoader очеедной глюк...");
        } catch (ClassNotFoundException e) {
            System.out.println("не нахожу класс...");
        } catch (NoSuchMethodException e) {
            System.out.println("нет метода для создания экземпляра через classLoader");
        } catch (InvocationTargetException e) {
            System.out.println("нет конструктора без полей...");
        }
        return null;
    }
}