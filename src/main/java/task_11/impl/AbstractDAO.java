package task_11.impl;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * класс реализует обобщенную логику Data Access Object
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
abstract class AbstractDAO {

    /**
     * удаляет запись из БД по переданному id
     *
     * @param id         номер записи в БД
     * @param connection подключение к БД
     * @param logger     логирование
     * @param sql        строка запроса формата sql
     */
    void delete(Connection connection, int id, Logger logger, String sql) {
        logger.debug(MessageFormat.format("delete query in %s have started", this.getClass().getName()));
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("delete query have done successfully");
            connection.commit();
        } catch (SQLException e) {
            easyRollBack(connection, logger);
        }
    }

    /**
     * безопасный останов авто комментирования
     *
     * @param connection подключение к БД
     * @param logger     логирование
     */
    void stopCommit(Connection connection, Logger logger) {
        logger.debug("try to disable an auto commit");
        try {
            connection.setAutoCommit(false);
            logger.debug("auto commit have disabled");
        } catch (SQLException e) {
            logger.error("Error : ", e);
        }
    }

    /**
     * безопасный откат состояния записи БД
     * в случае ошибки во время запроса
     *
     * @param connection подключение к БД
     * @param logger     логирование
     */
    void easyRollBack(Connection connection, Logger logger) {
        logger.debug("rollback have started");
        try {
            connection.rollback();
            logger.debug("rollback have finished successfully");
        } catch (SQLException e) {
            logger.error("Error : ", e);
        }
    }
}
