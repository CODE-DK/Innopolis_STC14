package task_11.impl;

import org.apache.log4j.Logger;
import task_11.dao.SubjectDAO;
import task_11.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectDAOimpl implements SubjectDAO {

    /**
     * класс поддерживает логгирование в выходной файл в директории log
     */
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
        LOGGER.info("create query in Subject have started");
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setString(1, subject.getDescription());
            ResultSet set = statement.executeQuery();
            LOGGER.info("create query have done successfully with " + subject.getDescription() + " params");
            if (set.next()) {
                subject.setId(set.getInt(SUBJECT_ID));
            }
            LOGGER.info("returns id " + subject.getId());
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, create block stop with " + e);
            System.out.println("e = " + e);
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
        LOGGER.info("select query in Subject have started");
        Subject subject = new Subject();
        try {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            LOGGER.info("select query have done successfully, start reading an answer");
            if (set.next()) {
                subject.setId(set.getInt(SUBJECT_ID));
                subject.setDescription(set.getString(SUBJECT_DESC));
            }
            LOGGER.info("select query reading have done successfully with "
                    + subject.getId() + " " + subject.getDescription() + " params");
            connection.commit();
            return subject;
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, select block stop with " + e);
            System.out.println("e = " + e);
        }
        return null;
    }

    /**
     * обновляет запись в БД по переданному индексу,
     * хранящемуся в передаваемом объекте, данные обновляются
     * в соответствии с данными в объекте
     *
     * @param subject объект с данными для обновления записи
     */
    public void update(Subject subject) {
        LOGGER.info("update query in Subject have started");
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, subject.getDescription());
            statement.setInt(2, subject.getId());
            statement.executeUpdate();
            LOGGER.info("update query have done successfully with "
                    + subject.getId() + " " + subject.getDescription() + " params");
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, update block stop with " + e);
            System.out.println("e = " + e);
        }
    }

    /**
     * удаляет запись из БД по переданному id
     *
     * @param id номер записи в БД
     */
    @Override
    public void delete(int id) {
        LOGGER.info("delete query in Subject have started");
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            statement.executeUpdate();
            LOGGER.info("delete query have done successfully");
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Error : " + e + " --try to make a rollback");
            easyRollBack(connection);
            LOGGER.error("rollback have done successfully, delete block stop with " + e);
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
