package task_6;

import java.beans.Transient;
import java.util.Objects;
import java.util.Random;

/**
 * класс предназначен для тестирования сериализации в xml
 * важно чтобы поля для сериализуемого класса были простыми типами
 */

@XmlSerialize
public class Unit {

    private int iD;
    private String name;
    private long ip;

    @XmlTransient
    private int hashCode = new Random().nextInt();

    public Unit() {

    }

    Unit(int iD, String name, long ip) {
        this.iD = iD;
        this.name = name;
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return iD == unit.iD &&
                ip == unit.ip &&
                Objects.equals(name, unit.name);
    }

    @Override
    public int hashCode() {
        return hashCode + Objects.hash(iD, name, ip);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "iD=" + iD +
                ", name='" + name + '\'' +
                ", ip=" + ip +
                '}';
    }
}
