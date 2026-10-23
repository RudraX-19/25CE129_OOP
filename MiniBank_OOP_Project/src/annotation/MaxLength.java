package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* ================================================================
   Practical 7 — Part B (@MaxLength annotation)
   ================================================================ */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MaxLength {
    int value();
    String message() default "exceeds maximum length";
}
