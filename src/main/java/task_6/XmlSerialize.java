package task_6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * аннотирует класс содержащий поля приметивного типа
 * для его дальнейшей сериализации и десериализации
 * классами {@link XmlInputStream} {@link XmlOutputStream}
 * аннотация допустима только для классов
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface XmlSerialize {

}
