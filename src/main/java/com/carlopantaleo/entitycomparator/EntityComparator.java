package com.carlopantaleo.entitycomparator;

import com.google.common.collect.Ordering;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A handy Comparator for comparing objects lists, dynamically choosing the properties to compare. Particularly handy
 * for CRUD views where order is not defined on the database side.
 *
 * @param <T> the object type.
 */
public class EntityComparator<T> implements Comparator<T> {
    private List<Method> propertyGetters = new ArrayList<>();
    private Class<T> clazz;

    public EntityComparator(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Adds a property to take into account during comparation. Properties are compared in the order they are added.
     *
     * @param name property name.
     * @throws NoSuchMethodException if property getter is not defined.
     */
    public void addComparingProperty(String name) throws NoSuchMethodException {
        String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Method getter = clazz.getMethod(methodName);
        propertyGetters.add(getter);
    }

    /**
     * Implements the {@link Comparator#compare} method.
     *
     * @param o1 the first object to compare.
     * @param o2 the second object to compare.
     * @throws RuntimeException if either a property getter is not public, or it has thrown an exception (in the last
     *                          case, the originating exception is accessible through {@link
     *                          RuntimeException#getCause()}).
     */
    @Override
    public int compare(T o1, T o2) {
        for (Method propertyGetter : propertyGetters) {
            try {
                Object res1 = propertyGetter.invoke(o1);
                Object res2 = propertyGetter.invoke(o2);

                int comparingResult;
                if (res1 instanceof Comparable<?> && res2 instanceof Comparable<?>) {
                    comparingResult =
                            Ordering.<Comparable<?>>natural().compare(((Comparable<?>) res1), ((Comparable<?>) res2));
                } else {
                    // Ugly, but better than nothing
                    comparingResult = Ordering.usingToString().compare(res1, res2);
                }

                if (comparingResult != 0) {
                    return comparingResult;
                }
            } catch (IllegalAccessException e) {
                // This exception should never be thrown, because of the 'fail fast' pattern in addComparingProperty,
                // which won't let one add a private getter.
                throw new RuntimeException("Method " + propertyGetter.getName() + " is not public", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Exception while calling getter " + propertyGetter.getName(), e.getCause());
            }
        }

        return 0;
    }
}
