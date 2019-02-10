package task_11.impl;

import org.apache.log4j.Logger;
import task_11.dao.SubjectDAO;
import task_11.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class SubjectDAOimpl extends AbstractDAO implements SubjectDAO {

    private static final Logger LOGGER = Logger.getLogger(SubjectDAOimpl.class);

    /**
     * набор sql комманд для реализации связи многие ко многим
     */
    private static final String CREATE = "insert into subject (subject_desc) values (?) returning subject_id";
    private static final String READ = "select * from subject where subject_id = ?";
    private static final String UPDATE = "update subject set subject_desc = ? where subject_id = ?";
    private static final String DELETE = "delete from subject where subject_id = ?";

    /**
     * поля соответствующе имена в таблицах
     */
    private static final String SUBJECT_ID = "subject_id";
    private static final String SUBJECT_DESC = "subject_desc";

    /**
     * соединение с БД
     */
    private final Connection connection;

    public SubjectDAOimpl(Connection connection) {
        this.connection = connection;
        stopCommit(connection);
    }

    /**
     * создает запись в БД записывая поля переданного
     * экземпляра в таблицу соответствующего имени
     *
     * @param subject объект по которому будет создана запись в БД
     */
    @Override
    public void create(Subject subject) {
        LOGGER.debug("create query in Subject have started");
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setString(1, subject.getDescription());
            ResultSet set = statement.executeQuery();
            LOGGER.debug(String.format
                    (
                            "create query have done successfully with %s params", subject.getDescription()
                    ));
            if (set.next()) {
                subject.setId(set.getInt(SUBJECT_ID));
            }
            LOGGER.debug(String.format("returns id = %s", subject.getId()));
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
    public Subject select(int id) {
        LOGGER.debug("select query in Subject have started");
        Subject subject = new Subject();
        try {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            LOGGER.debug("select query have done successfully, start reading an answer");
            if (set.next()) {
                subject.setId(set.getInt(SUBJECT_ID));
                subject.setDescription(set.getString(SUBJECT_DESC));
            }
            LOGGER.debug(String.format
                    (
                            "select query reading have done successfully with %s %s params"
                            , subject.getId(), subject.getDescription()
                    ));
            connection.commit();
            return subject;
        } catch (SQLException e) {
            easyRollBack(connection);
        }
        return subject;
    }

    /**
     * обновляет запись в БД по переданному индексу,
     * хранящемуся в передаваемом объекте, данные обновляются
     * в соответствии с данными в объекте
     *
     * @param subject объект с данными для обновления записи
     */
    public void update(Subject subject) {
        LOGGER.debug("update query in Subject have started");
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, subject.getDescription());
            statement.setInt(2, subject.getId());
            statement.executeUpdate();
            LOGGER.debug(String.format
                    (
                            "update query have done successfully with %s %s params"
                            , subject.getId(), subject.getDescription()
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
