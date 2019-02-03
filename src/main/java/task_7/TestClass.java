package task_7;

import java.lang.reflect.InvocationTargetException;

/**
 * task_9 class
 */
public class TestClass {

    private static final String CLASS_NAME = "SomeClass";

    public static String getClassName() {
        return CLASS_NAME;
    }

    public static void main(String[] args) {

        Compiler java = new Compiler(CLASS_NAME);
        java.compile();

        ClassLoader loader = new CompileLoader();
        Object o = getObject(loader);
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
     * @param loader класс - загрузчик
     * @return экземпляр требуемого класса в соответствии с используемым именем
     */
    private static Object getObject(ClassLoader loader) {
        try {
            Class<?> clazz = loader.loadClass(CLASS_NAME);
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