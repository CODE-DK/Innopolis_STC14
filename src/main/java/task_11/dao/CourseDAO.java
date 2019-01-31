package task_11.dao;

import task_11.entity.Person;
import task_11.entity.Subject;
import java.util.List;

/**
 * интерфейс реализует логику Data Access Object
 * для класса Course для последующего взаимодействия с БД
 * таблица является связующей сущностью для реализации
 * связи многие ко многим
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public interface CourseDAO {

    /**
     * метод для получения списка связанных сущностей
     * по id с переданным объектом subject
     *
     * @param subject объект для поиска записей по таблице
     * @return список связанных сущностей из таблицы person
     */
    List<Person> getPersonsBySubject(Subject subject);

    /**
     * метод для получения списка связанных сущностей
     * по id с переданным объектом person
     *
     * @param person объект для поиска записей по таблице
     * @return список связанных сущностей из таблицы subject
     */
    List<Subject> getSubjectsByPerson(Person person);

    /**
     * метод для создания связи один ко многим внутри таблицы
     * реализует связь : один person много subjects
     *
     * @param person объект Person - root элемент связи
     * @param subjects дочерние связи типа Subject
     */
    void create(Person person, List<Subject> subjects);

    /**
     * метод для создания связи один ко многим внутри таблицы
     * реализует связь : один subject много people
     *
     * @param subject объект Subject - root элемент связи
     * @param people дочерние связи типа Person
     */
    void create(Subject subject, List<Person> people);
}
