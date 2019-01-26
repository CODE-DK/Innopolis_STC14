package task_6;

/**
 * класс предназначен для тестирования сериализации в xml
 * важно чтобы поля для сериализуемого класса были простыми типами
 */

@XmlSerialize
public class Unit {

    private int iD;
    private String name;
    private long ip;

    public Unit() {

    }

    Unit(int iD, String name, long ip) {
        this.iD = iD;
        this.name = name;
        this.ip = ip;
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
