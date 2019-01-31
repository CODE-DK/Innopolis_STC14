package task_11.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * POJO класс для хранения и работы с данными из БД
 * ссылается на таблицу subject
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
@Data
public class Subject {

    private int id;
    private String description;
    private List<Person> people;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id &&
                Objects.equals(description, subject.description) &&
                Objects.equals(people, subject.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, people);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", people=" + people +
                '}';
    }
}
