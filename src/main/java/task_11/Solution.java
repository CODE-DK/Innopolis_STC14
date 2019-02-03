package task_11;

import org.apache.log4j.Logger;
import task_11.dao.CourseDAO;
import task_11.dao.PersonDAO;
import task_11.dao.SubjectDAO;
import task_11.entity.Person;
import task_11.entity.Subject;
import task_11.impl.CourseDAOimpl;
import task_11.impl.PersonDAOimpl;
import task_11.impl.SubjectDAOimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * тестовый класс для подключения к базе данных используя jdbc api
 * и работы с ней, в качестве базы данных используется postgresql
 * работающая на локальном хосте
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class Solution {

    /**
     * для логирования состояния взят класс Logger из пакета org.slf4j
     * логирование происходит в выходной файл, размер файла не превышает 50 Мб
     * директория к файлу test_1/log/logs.log
     */
    private static final Logger LOGGER = Logger.getLogger(Solution.class);
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DRIVER = "org.postgresql.Driver";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            LOGGER.info("created connection to DB");
            loadJdbcDriver();

            PersonDAO personDAO = new PersonDAOimpl(connection);
            SubjectDAO subjectDAO = new SubjectDAOimpl(connection);
            CourseDAO courseDAO = new CourseDAOimpl(connection);

            /*
             * данный блок кода закоментирован умышлено так как работа с базой данных
             * не подразумевает частого перекомпилирования кода. при проведение теста
             * достаточно снять комментарий с одного или нескольких тестов,
             * необходимых для выполнения
             */

            /*
            createTest(personDAO, subjectDAO);
            selectTest(personDAO, subjectDAO);
            updateTest(personDAO, subjectDAO);
            deleteTest(personDAO, subjectDAO);
            testPersonBySubject(subjectDAO, courseDAO);
            testSubjectByPerson(personDAO, courseDAO);
            */

        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException from Solution class");
        } catch (SQLException e) {
            LOGGER.error("SQLException from Solution class");
        }
    }

    /**
     * метод тестирует взаимодействие один ко многим
     * со стороны personDAO связь осуществляется с таблицей courseDAO
     * результатом запроса является список Subject's связанных по id
     * с полученым по id person, id выбран произвольно из имеющихся в таблице
     *
     * @param personDAO DAO для Person.class
     * @param courseDAO DAO для Course.class
     */
    private static void testSubjectByPerson(PersonDAO personDAO, CourseDAO courseDAO) {
        LOGGER.info("Course test start : task - find subject by person");
        long start = System.nanoTime();
        Person person = personDAO.select(41);
        List<Subject> subjects = courseDAO.getSubjectsByPerson(person);
        person.setSubjects(subjects);
        long end = System.nanoTime() - start;
        System.out.println(person);
        LOGGER.info("Course test end, time="+end);
    }

    /**
     * метод тестирует взаимодействие один ко многим
     * со стороны subjectDAO связь осуществляется с таблицей courseDAO
     * результатом запроса является список Person's связанных по id
     * с полученым по id subject, id выбран произвольно из имеющихся в таблице
     *
     * @param subjectDAO DAO для Person.class
     * @param courseDAO  DAO для Course.class
     */
    private static void testPersonBySubject(SubjectDAO subjectDAO, CourseDAO courseDAO) {
        LOGGER.info("Course test start : task - find person by subject");
        long start = System.nanoTime();
        Subject subject = subjectDAO.select(27);
        List<Person> people = courseDAO.getPersonsBySubject(subject);
        subject.setPeople(people);
        long end = System.nanoTime() - start;
        System.out.println(subject);
        LOGGER.info("Course test end, time="+end);
    }

    /**
     * метод тестирует функционал обновления данных в БД
     * по переданным объектам. необходимо соблюдать согласование id
     * полей объектов с полями БД так как данное поле используется в качестве primary_key
     * и автоматически присваевается объекту при занесении в таблицу
     *
     * @param personDAO  DAO для Person.class
     * @param subjectDAO DAO для Subject.class
     */
    private static void updateTest(PersonDAO personDAO, SubjectDAO subjectDAO) {
        LOGGER.info("UPDATE test have started");
        Person person = new Person();
        person.setId(41);
        person.setName("Bobby");
        person.setBirthDate(System.nanoTime());
        personDAO.update(person);
        LOGGER.info("UPDATE test with PersonDAO have completed");
        Subject subject = new Subject();
        subject.setId(26);
        subject.setDescription("astronomy");
        subjectDAO.update(subject);
        LOGGER.info("UPDATE test with SubjectDAO have completed");
    }

    /**
     * метод тестирует функционал удаления данных из БД
     * по переданным объектам. необходимо соблюдать согласование id
     * полей объектов с полями БД так как данное поле используется в качестве primary_key
     * и автоматически присваевается объекту при занесении в таблицу
     *
     * @param personDAO  DAO для Person.class
     * @param subjectDAO DAO для Subject.class
     */
    private static void deleteTest(PersonDAO personDAO, SubjectDAO subjectDAO) {
        LOGGER.info("DELETE test have started");
        personDAO.delete(43);
        LOGGER.info("DELETE test with PersonDAO have completed");
        subjectDAO.delete(30);
        LOGGER.info("DELETE test with SubjectDAO have completed");
    }

    /**
     * метод тестирует функционал получения данных из БД
     * по переданным объектам. получение данных происходит
     * по переданному ключу id каждого объекта
     *
     * @param personDAO  DAO для Person.class
     * @param subjectDAO DAO для Subject.class
     */
    private static void selectTest(PersonDAO personDAO, SubjectDAO subjectDAO) {
        LOGGER.info("SELECT test have started");
        Person person = personDAO.select(40);
        LOGGER.info("SELECT test with PersonDAO have completed with parameters " + person);
        Subject subject = subjectDAO.select(26);
        LOGGER.info("SELECT test with SubjectDAO have completed with parameters " + subject);
    }

    /**
     * метод тестирует функционал создания очередной записи в БД
     * по переданным объектам. ключ id объектов будет автоматически присвоен после
     * успешного выполнения метода и специально задавать его не имеет смысла
     *
     * @param personDAO  DAO для Person.class
     * @param subjectDAO DAO для Subject.class
     */
    private static void createTest(PersonDAO personDAO, SubjectDAO subjectDAO) {
        LOGGER.info("CREATE test have started");
        Person person = new Person();
        person.setName("Alan");
        person.setBirthDate(System.nanoTime());
        personDAO.create(person);
        LOGGER.info("CREATE test with PersonDAO have completed");
        Subject subject = new Subject();
        subject.setDescription("English");
        subjectDAO.create(subject);
        LOGGER.info("CREATE test with SubjectDAO have completed");
    }

    /**
     * метод предназначен для загрузки драйвера в память jvm
     *
     * @throws ClassNotFoundException
     */
    private static void loadJdbcDriver() throws ClassNotFoundException {
        Class jdbc = Class.forName(DRIVER);
        LOGGER.info("uploaded jdbc driver");
    }
}
