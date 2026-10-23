// ============================================================
//  PRACTICLE 7 - Part A - Task 1: Form Validator
//  Custom annotations @NotBlank and @MaxLength(int);
//  a checker reads each field's annotations via reflection
//  File: PRACTICLE_7/FormValidator.java
// ============================================================

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// ---- Annotation definitions ----

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
    String message() default "must not be blank";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
    String message() default "exceeds maximum length";
}

// ---- Annotated form class ----

class SignupForm {
    @NotBlank
    private String username;

    @NotBlank
    @MaxLength(50)
    private String email;

    @NotBlank
    @MaxLength(20)
    private String password;

    private String referralCode; // no annotation — optional field

    public SignupForm(String username, String email, String password, String referralCode) {
        this.username     = username;
        this.email        = email;
        this.password     = password;
        this.referralCode = referralCode;
    }
}

// ---- Validator ----

class AnnotationChecker {
    public static List<String> validate(Object obj) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);

            // @NotBlank check
            if (field.isAnnotationPresent(NotBlank.class)) {
                if (value == null || value.toString().trim().isEmpty()) {
                    errors.add("Field '" + field.getName() + "': "
                             + field.getAnnotation(NotBlank.class).message());
                }
            }

            // @MaxLength check
            if (field.isAnnotationPresent(MaxLength.class)) {
                int max = field.getAnnotation(MaxLength.class).value();
                if (value != null && value.toString().length() > max) {
                    errors.add("Field '" + field.getName() + "': "
                             + field.getAnnotation(MaxLength.class).message()
                             + " (max " + max + ")");
                }
            }
        }
        return errors;
    }
}

// ---- Driver ----

public class FormValidator {
    public static void main(String[] args) throws IllegalAccessException {
        System.out.println("=== Form Validator using Annotations + Reflection ===\n");

        // Valid form
        SignupForm valid = new SignupForm("alice", "alice@example.com", "Secret@123", "REF01");
        List<String> errors1 = AnnotationChecker.validate(valid);
        System.out.println("Valid form errors: " + (errors1.isEmpty() ? "None" : errors1));

        // Invalid form — blank username, email too long
        String longEmail = "a".repeat(60) + "@example.com";
        SignupForm invalid = new SignupForm("", longEmail, "", null);
        List<String> errors2 = AnnotationChecker.validate(invalid);
        System.out.println("\nInvalid form errors:");
        for (String err : errors2) {
            System.out.println("  - " + err);
        }
    }
}
