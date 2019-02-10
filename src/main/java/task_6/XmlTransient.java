package task_6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * аннотирует поле не требующее сериализацию
 * либо поле, намерено исключаемое при сериализации
 *
 * @author Комовский Дмитрий
 * @version v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlTransient {
}
