package task_11.impl;

import org.apache.log4j.Logger;
import task_11.dao.PersonDAO;
import task_11.entity.Person;

import java.sql.*;
import java.text.MessageFormat;

/**
 * класс реализует логику Data Access Object реализуя PersonDAO
 * для таблицы person,имеет POJO реализациею entity.Person
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class PersonDAOimpl extends AbstractDAO implements PersonDAO {

    private static final Logger LOGGER = Logger.getLogger(PersonDAOimpl.class);

    /**
     * набор sql комманд для реализации связи многие ко многим
     */
    private static final String CREATE = "insert into person (person_name, person_date) values (?, ?) returning person_id";
    private static final String READ = "select * from person where person_id = ?";
    private static final String UPDATE = "update person set person_name = ?, person_date = ? where person_id = ?";
    private static final String DELETE = "delete from person where person_id = ?";

    /**
     * поля соответствующе имена в таблицах
     */
    private static final String PERSON_ID = "person_id";
    private static final String PERSON_NAME = "person_name";
    private static final String PERSON_DATE = "person_date";

    /**
     * соединение с БД
     */
    private final Connection connection;

    public PersonDAOimpl(Connection connection) {
        this.connection = connection;
        stopCommit(connection);
    }

    /**
     * создает запись в БД записывая поля переданного
     * экземпляра в таблицу соответствующего имени
     *
     * @param person объект по которому будет создана запись в БД
     */
    @Override
    public void create(Person person) {
        LOGGER.debug("create query in Person have started");
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setString(1, person.getName());
            statement.setTimestamp(2, new Timestamp(person.getBirthDate()));
            ResultSet set = statement.executeQuery();
            LOGGER.debug(String.format
                    (
                            "create query have done successfully with %s %s params"
                            , person.getName(), person.getBirthDate()
                    ));
            if (set.next()) {
                person.setId(set.getInt(PERSON_ID));
            }
            LOGGER.debug(String.format("returns id = %s", person.getId()));
            connection.commit();
        } catch (SQLException e) {
            easyRollBack(connection);
        }
    }

    /**
     * создает объект по переданному id
     * соответствующему id из БД
     *
     * @param id уникальный номер записи в БД
     * @return объект, созданный по данным из БД
     */
    public Person select(int id) {
        LOGGER.debug("select query in Person have started");
        Person person = new Person();
        try {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            LOGGER.debug("select query have done successfully, start reading an answer");
            if (set.next()) {
                person.setId(set.getInt(PERSON_ID));
                person.setName(set.getString(PERSON_NAME));
                person.setBirthDate(set.getDate(PERSON_DATE).getTime());
            }
            LOGGER.debug(String.format
                    (
                            "select query reading have done successfully with %s %s %s params",
                            person.getId(), person.getName(), person.getBirthDate()
                    ));
            connection.commit();
            return person;
        } catch (SQLException e) {
            easyRollBack(connection);
        }
        return person;
    }

    /**
     * обновляет запись в БД по переданному индексу,
     * хранящемуся в передаваемом объекте, данные обновляются
     * в соответствии с данными в объекте
     *
     * @param person объект с данными для обновления записи
     */
    public void update(Person person) {
        LOGGER.debug("update query in Person have started");
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, person.getName());
            statement.setTimestamp(2, new Timestamp(person.getBirthDate()));
            statement.setInt(3, person.getId());
            statement.executeUpdate();
            LOGGER.debug(String.format
                    (
                            "update query reading have done successfully with %s %s %s params"
                            , person.getId(), person.getName(), person.getBirthDate()
                    ));
            connection.commit();
        } catch (SQLException e) {
            easyRollBack(connection);
        }
    }

    /**
     * удаляет запись из БД по переданному id
     *
     * @param id номер записи в БД
     */
    @Override
    public void delete(int id) {
        super.delete(connection, id, LOGGER, DELETE);
    }

    private void stopCommit(Connection connection) {
        super.stopCommit(connection, LOGGER);
    }

    private void easyRollBack(Connection connection) {
        super.easyRollBack(connection, LOGGER);
    }
}
