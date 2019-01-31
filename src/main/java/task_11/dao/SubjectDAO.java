package task_11.dao;

import task_11.entity.Subject;

/**
 * интерфейс реализует логику Data Access Object
 * для класса Subject для последующего взаимодействия с БД
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public interface SubjectDAO {

    /**
     * создает запись в БД записывая поля переданного
     * экземпляра в таблицу соответствующего имени
     *
     * @param subject объект по которому будет создана запись в БД
     */
    void create(Subject subject);

    /**
     * создает объект по переданному id
     * соответствующему id из БД
     *
     * @param id уникальный номер записи в БД
     * @return объект, созданный по данным из БД
     */
    Subject select(int id);

    /**
     * обновляет запись в БД по переданному индексу,
     * хранящемуся в передаваемом объекте, данные обновляются
     * в соответствии с данными в объекте
     *
     * @param subject объект с данными для обновления записи
     */
    void update(Subject subject);

    /**
     * удаляет запись из БД по переданному id
     *
     * @param id номер записи в БД
     */
    void delete(int id);
}
