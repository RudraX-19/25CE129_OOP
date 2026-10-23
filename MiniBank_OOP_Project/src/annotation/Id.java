package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* ================================================================
   Practical 7 — Part B (@Id annotation)
   Marker annotation to identify the id field of an entity.
   ================================================================ */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
}
