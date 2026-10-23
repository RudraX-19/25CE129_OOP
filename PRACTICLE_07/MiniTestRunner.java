// ============================================================
//  PRACTICLE 7 - Part A - Task 2: Mini Test Runner
//  @Run annotation marks no-arg methods; reflection finds and
//  invokes only those methods — the idea behind JUnit @Test
//  File: PRACTICLE_7/MiniTestRunner.java
// ============================================================

import java.lang.annotation.*;
import java.lang.reflect.Method;

// ---- @Run annotation ----

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {}

// ---- A class with some methods marked @Run ----

class MathTests {

    @Run
    public void testAddition() {
        int result = 2 + 3;
        System.out.println("  testAddition: 2+3 = " + result
                         + " => " + (result == 5 ? "PASS" : "FAIL"));
    }

    @Run
    public void testMultiplication() {
        int result = 4 * 5;
        System.out.println("  testMultiplication: 4*5 = " + result
                         + " => " + (result == 20 ? "PASS" : "FAIL"));
    }

    @Run
    public void testSubtraction() {
        int result = 10 - 7;
        System.out.println("  testSubtraction: 10-7 = " + result
                         + " => " + (result == 3 ? "PASS" : "FAIL"));
    }

    // NOT annotated — should NOT be invoked by the runner
    public void helperMethod() {
        System.out.println("  helperMethod: this should NOT run via @Run");
    }

    public void anotherHelper() {
        System.out.println("  anotherHelper: this should NOT run via @Run");
    }
}

// ---- Runner ----

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Mini Test Runner using @Run + Reflection ===\n");

        Class<?> testClass = MathTests.class;
        Object   instance  = testClass.getDeclaredConstructor().newInstance();

        Method[] methods   = testClass.getDeclaredMethods();
        int      ranCount  = 0;
        int      skipped   = 0;

        System.out.println("Scanning " + testClass.getSimpleName() + " for @Run methods...\n");

        for (Method method : methods) {
            if (method.isAnnotationPresent(Run.class)) {
                System.out.println("Running: " + method.getName() + "()");
                method.invoke(instance);
                ranCount++;
            } else {
                System.out.println("Skipping: " + method.getName() + "() [no @Run]");
                skipped++;
            }
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Methods with @Run executed : " + ranCount);
        System.out.println("Methods skipped            : " + skipped);
    }
}
