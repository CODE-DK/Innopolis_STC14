package task_2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provide and expand a {@link task_2.ObjectBox}
 * class, by adding two methods {@code summator(), splitter()}.
 * Notice that this class can throw {@link ClassCastException} but catch
 * them internally.
 *
 * @param <T> can be any Number child or Number as well
 * @author Komovskiy Dmitriy
 * @version 17.01.19 v1.0
 */

public class MathBox<T extends Number> extends ObjectBox {

    /**
     * Constructs a newly allocated {@code MathBox} object that
     * collect according on {@link ObjectBox} as a superclass.
     *
     * @param mass is an input array parametrised with generic
     *             {@code T extends java.lang.Number}
     */
    public MathBox(T[] mass) {
        super(mass);
    }

    /**
     * this method returns a sum of all element
     * extends {@link java.lang.Number} as {@link java.lang.Double}
     *
     * @return double value containse a sum of all elements
     * from {@code super.list}
     */
    double summator() {

        double sum = 0d;

        for (Object o : super.getList()) {
            sum += ((Number) o).doubleValue();
        }
        return sum;
    }

    /**
     * this method return true when an element successfully
     * will add in the internal {@link java.util.Collection} an {@link java.lang.Object}
     * and overrides this method from parent. <p/>
     * Notice: you can add only values extends {@link java.lang.Number}
     * otherwise the {@link ClassCastException} will be trowed.
     *
     * @param o can be a Number type only
     * @return it returns true when the value will successfully adds
     */

    @Override
    public boolean addObject(Object o) {
        try {
            if (o instanceof Number) {
                return super.addObject(o);
            } else {
                throw new ClassCastException("Could not cast <Object> to <Number>");
            }
        } catch (ClassCastException e) {
            System.out.println("В ObjectBox методе addObject : " + e);
        }
        return false;
    }

    /**
     * this method provide a split logic for all elements
     * included in parent list with attention on type of value.
     *
     * @return list of numbers that were divided
     */
    List<Number> splitter(int div) {

        List<Number> outSet = new ArrayList<>();

        for (Object o : super.getList())
            if (o instanceof Byte || o instanceof Short || o instanceof Integer) {
                outSet.add(((Number) o).intValue() / div);
            } else if (o instanceof Float || o instanceof Double) {
                outSet.add(((Number) o).doubleValue() / div);
            } else if (o instanceof Long) {
                outSet.add(((Number) o).longValue() / div);
            }

        return outSet;
    }

    /**
     * Returns a {@code String} object view of this
     * {@code MathBox} object. The value consists of the
     * simple name of class and all field inside.
     *
     * @return a string view of this {@code MathBox} object
     */
    @Override
    public String toString() {
        return super.toString().replaceAll
                ("ObjectBox", "MathBox");
    }

    /**
     * Returns a hash code for this {@code MathBox}.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return 2 * super.hashCode();
    }
}
