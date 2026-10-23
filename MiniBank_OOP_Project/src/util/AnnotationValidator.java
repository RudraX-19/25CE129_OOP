package util;

import annotation.Id;
import annotation.MaxLength;
import annotation.Positive;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* ================================================================
   Practical 7 — Part B (AnnotationValidator)
   Reads @Positive and @MaxLength rules at runtime via reflection.
   ================================================================ */

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        if (obj == null) {
            return new String[]{"Object is null"};
        }
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);

                // @Positive check (applies to numeric fields)
                if (field.isAnnotationPresent(Positive.class)) {
                    if (value instanceof Number n) {
                        if (n.doubleValue() <= 0) {
                            errors.add("Field '" + field.getName() + "': "
                                    + field.getAnnotation(Positive.class).message());
                        }
                    }
                }

                // @MaxLength check (applies to String fields)
                if (field.isAnnotationPresent(MaxLength.class)) {
                    if (value instanceof String s) {
                        int max = field.getAnnotation(MaxLength.class).value();
                        if (s.length() > max) {
                            errors.add("Field '" + field.getName() + "': "
                                    + field.getAnnotation(MaxLength.class).message()
                                    + " (max " + max + ")");
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Field '" + field.getName() + "': cannot access");
            }
        }
        return errors.toArray(new String[0]);
    }
}
