// ============================================================
//  PRACTICLE 4 - Part A - Task 1: Password Strength
//  File: PRACTICLE_4/PasswordChecker.java
// ============================================================

class PasswordChecker {
    public static String strength(String pw) {
        int passedRules = 0;
        
        // Rule 1: length >= 8
        boolean rule1 = pw.length() >= 8;
        if (rule1) passedRules++;
        
        // Rule 2: contains uppercase letter
        boolean rule2 = pw.matches(".*[A-Z].*");
        if (rule2) passedRules++;
        
        // Rule 3: contains digit
        boolean rule3 = pw.matches(".*[0-9].*");
        if (rule3) passedRules++;
        
        // Rule 4: contains special character
        boolean rule4 = pw.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        if (rule4) passedRules++;
        
        System.out.println("  Rules passed: " + passedRules + "/4");
        System.out.println("    Length >= 8: " + rule1);
        System.out.println("    Uppercase: " + rule2);
        System.out.println("    Digit: " + rule3);
        System.out.println("    Special char: " + rule4);
        
        if (passedRules <= 1) {
            return "Weak";
        } else if (passedRules <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}

public class PasswordStrengthTest {
    public static void main(String[] args) {
        System.out.println("=== Password Strength Checker ===\n");
        
        String[] passwords = {
            "abc",
            "abcdefgh",
            "Abcdefgh",
            "Abcd1234",
            "Abcd1234!",
            "Pass123!",
            "weak"
        };
        
        for (String pw : passwords) {
            System.out.println("Password: \"" + pw + "\"");
            String strength = PasswordChecker.strength(pw);
            System.out.println("  Strength: " + strength);
            System.out.println();
        }
    }
}
