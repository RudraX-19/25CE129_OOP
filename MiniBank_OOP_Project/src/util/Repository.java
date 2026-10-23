package util;

import annotation.Id;
import model.Account;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/* ================================================================
   Practical 13 — Part B (Generic Repository)
   Stores any type safely, finds each object's key through @Id
   annotation by reflection. Includes bounded type / varargs method.
   ================================================================ */

public class Repository<T> {
    private final HashMap<String, T> store = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        String id = findId(entity);
        store.put(id, entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public T findById(String id) {
        return store.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }

    public int size() {
        return store.size();
    }

    @SuppressWarnings("unchecked")
    private String findId(T entity) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    return value != null ? value.toString() : null;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot read @Id field: " + field.getName(), e);
                }
            }
        }
        // Also check superclass fields
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            for (Field field : superClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        return value != null ? value.toString() : null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot read @Id field: " + field.getName(), e);
                    }
                }
            }
        }
        throw new RuntimeException("No @Id field found on " + clazz.getName());
    }

    /* ================================================================
       Practical 13 — Part B (bounded type parameter method)
       Works only for Number subtypes.
       ================================================================ */
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        double sum = 0;
        for (N n : values) {
            sum += n.doubleValue();
        }
        return sum / values.length;
    }
}
