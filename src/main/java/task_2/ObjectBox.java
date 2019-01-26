package task_2;

import java.util.*;

/**
 * This class created as a container for different types
 * of object's and realize different data structure's. As default
 * realisation the author have chosen a {@code TreeSet} to provide
 * a sorting of unique element.
 *
 * @author Komovskiy Dmitriy
 * @version 17.01.19 v1.0
 */
public class ObjectBox<T>{

    /**
     * Consist of {@code Object}'s and works like a
     * buffering structure. Implementation depends on the aim.
     * In this case the main task_6 to provide a unique sorted
     * list of objects were has been resolved by using {@code ThreeSet}
     */
    private Collection<T> list = new TreeSet<>();

    /**
     * hashCode created during refactoring
     */
    private final int hashcode;

    /**
     * Constructs a newly allocated {@code ObjectBox} object that
     * init {@code list} value from input {@code mass} massive.
     *
     * @param mass is an input array parametrised with generic
     *             {@code java.lang.Object}
     */
    public ObjectBox(T[] mass) {
        Collections.addAll(list, mass);
        hashcode = new Random().nextInt();
    }

    /**
     * Getter fo {@code list} field
     *
     * @return the list of {@code Object}'s as a {@link java.util.Collection}
     */
    Collection getList() {
        return list;
    }

    /**
     * this method add's new value to {@code list}
     *
     * @param o is {@link java.lang.Object}
     * @return true if adding has completed
     * successfully and false otherwise
     */
    public boolean addObject(T o) {
        return list.add(o);
    }

    /**
     * this method remove's value from {@code list}
     *
     * @param o is {@link java.lang.Object}
     * @return true if delete has completed
     * successfully and false otherwise
     */
    boolean deleteObject(T o) {
        return list.remove(o);
    }

    /**
     * this method returns a {@code String} view of the
     * {@link java.util.Collection} list which is a part of
     * {@code ObjectBox}
     *
     * @return a {@code String} view of the {@link java.util.Collection} list
     */
    String dump() {
        return list.toString();
    }

    /**
     * Returns a {@code String} object view of this
     * {@code ObjectBox} object. The value consists of the
     * simple name of class and all field inside.
     *
     * @return a string view of this {@code ObjectBox} object
     */
    @Override
    public String toString() {
        return "ObjectBox{" +
                "list=" + list +
                '}';
    }

    /**
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is an {@code ObjectBox} object that
     * contains the same {@code list} field as this object.
     *
     * @param obj the object to compare with.
     * @return {@code true} if the objects are the same;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ObjectBox<?> objectBox = (ObjectBox<?>) obj;
        return Objects.equals(list, objectBox.list);
    }

    /**
     * Returns a hash code for this {@code MathBox}.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return hashcode;
    }
}
