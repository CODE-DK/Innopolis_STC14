package task_11.impl;

import org.apache.log4j.Logger;
import task_11.dao.CourseDAO;
import task_11.entity.Person;
import task_11.entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * класс реализует логику Data Access Object реализуя CourseDAO
 * для таблицы Course, не является POJO реализацией и представляет
 * логическую сущность для связи многие ко многим таблиц
 * person & subject
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class CourseDAOimpl implements CourseDAO {

    /**
     * класс поддерживает логгирование в выходной файл в директории log
     */
    private static final Logger LOGGER = Logger.getLogger(CourseDAOimpl.class);

    /**
     * набор sql комманд для реализации связи многие ко многим
     */
    private static final String CREATE = "insert into course(person_id, subject_id) values (?,?)";
    private static final String PERSONS =
            "select distinct course.person_id, person_name, person_date " +
                    "from course " +
                    "left join person on course.person_id = person.person_id " +
                    "where subject_id = ?";
    private static final String SUBJECTS =
            "select distinct course.subject_id, subject_desc " +
                    "from course " +
                    "left join subject s on course.subject_id = s.subject_id " +
                    "where person_id = ?";

    /**
     * поля соответствующе имена в таблицах
     */
    private static final String PERSON_ID = "person_id";
    private static final String PERSON_NAME = "person_name";
    private static final String PERSON_DATE = "person_date";
    private static final String SUBJECT_ID = "subject_id";
    private static final String SUBJECT_DESC = "subject_desc";
    /**
     * соединение с БД
     */
    private final Connection connection;

    public CourseDAOimpl(Connection connection) {
        this.connection = connection;
        stopCommit(connection);
    }

    /**
     * метод для получения списка связанных сущностей
     * по id с переданным объектом subject
     *
     * @param subject объект для поиска записей по таблице
     * @return список связанных сущностей из таблицы person
     */
    @Override
    public List<Person> getPersonsBySubject(Subject subject) {
        LOGGER.info("start with getting people by subject");
        List<Person> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(PERSONS)) {
            statement.setInt(1, subject.getId());
            LOGGER.info("trying with query");
            ResultSet set = statement.executeQuery();
            LOGGER.info("query have done successfully");
            while (set.next()) {
                Person person = new Person();
                person.setId(set.getInt(PERSON_ID));
                person.setName(set.getString(PERSON_NAME));
                person.setBirthDate(set.getDate(PERSON_DATE).getTime());
                result.add(person);
            }
            connection.commit();
            LOGGER.info("commit");
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, create block stop with " + e);
            System.out.println("e = " + e);
        }
        return result;
    }

    /**
     * метод для получения списка связанных сущностей
     * по id с переданным объектом person
     *
     * @param person объект для поиска записей по таблице
     * @return список связанных сущностей из таблицы subject
     */
    @Override
    public List<Subject> getSubjectsByPerson(Person person) {
        LOGGER.info("start with getting subjects by person");
        List<Subject> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SUBJECTS)) {
            statement.setInt(1, person.getId());
            LOGGER.info("trying with query");
            ResultSet set = statement.executeQuery();
            LOGGER.info("query have done successfully");
            while (set.next()) {
                Subject subject = new Subject();
                subject.setId(set.getInt(SUBJECT_ID));
                subject.setDescription(set.getString(SUBJECT_DESC));
                result.add(subject);
            }
            connection.commit();
            LOGGER.info("commit");
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, create block stop with " + e);
            System.out.println("e = " + e);
        }
        return result;
    }

    /**
     * метод для создания связи один ко многим внутри таблицы
     * реализует связь : один person много subjects
     *
     * @param person объект Person - root элемент связи
     * @param subjects дочерние связи типа Subject
     */
    @Override
    public void create(Person person, List<Subject> subjects) {
        LOGGER.info("one person to many subjects");
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            for (Subject subject : subjects) {
                statement.setInt(1, person.getId());
                statement.setInt(2, subject.getId());
                statement.executeUpdate();
            }
            connection.commit();
            LOGGER.info("create query have done successfully");
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, create block stop with " + e);
            System.out.println("e = " + e);
        }
    }

    /**
     * метод для создания связи один ко многим внутри таблицы
     * реализует связь : один subject много people
     *
     * @param subject объект Subject - root элемент связи
     * @param people дочерние связи типа Person
     */
    @Override
    public void create(Subject subject, List<Person> people) {
        LOGGER.info("one subject to many persons");
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            for (Person person : people) {
                statement.setInt(1, person.getId());
                statement.setInt(2, subject.getId());
                statement.executeUpdate();
            }
            connection.commit();
            LOGGER.info("create query have done successfully");
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, create block stop with " + e);
            System.out.println("e = " + e);
        }
    }

    private void stopCommit(Connection connection) {
        LOGGER.info("try to disable an auto commit");
        try {
            connection.setAutoCommit(false);
            LOGGER.info("auto commit have disabled");
        } catch (SQLException e) {
            LOGGER.error("Error : " + e);
            System.out.println("e = " + e);
        }
    }

    private void easyRollBack(Connection connection) {
        LOGGER.info("rollback have started");
        try {
            connection.rollback();
            LOGGER.info("rollback have finished successfully");
        } catch (SQLException e) {
            LOGGER.error("Error : " + e);
            System.out.println("ex = " + e);
        }
    }
}
