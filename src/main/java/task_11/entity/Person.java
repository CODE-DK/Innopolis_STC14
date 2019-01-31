package task_11.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * POJO класс для хранения и работы с данными из БД
 * ссылается на таблицу person
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
@Data
public class Person {

    private int id;
    private String name;
    private long birthDate;
    private List<Subject> subjects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                birthDate == person.birthDate &&
                Objects.equals(name, person.name) &&
                Objects.equals(subjects, person.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, subjects);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", subjects=" + subjects +
                '}';
    }
}
