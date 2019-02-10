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
 *
 * @author Комовский Дмитрий
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface XmlSerialize {

}
