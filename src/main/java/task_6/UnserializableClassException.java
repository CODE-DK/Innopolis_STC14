package task_6;

/**
 * исколючение, будет сгенерировано при попытке
 * сериализовать класс, не анотированый @XmlSerialize
 */
public class UnserializableClassException extends RuntimeException{

    public UnserializableClassException(String message) {
        super(message);
    }
}
