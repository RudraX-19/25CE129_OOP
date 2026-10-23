package util;

import java.util.regex.Pattern;

/* ================================================================
   Practical 4 — Part B (Validator)
   Regex checks for mobile, email, PAN and IFSC.
   ================================================================ */

public class Validator {
    private static final Pattern MOBILE = Pattern.compile("[6-9][0-9]{9}");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PAN = Pattern.compile("[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}");
    private static final Pattern IFSC = Pattern.compile("[A-Za-z]{4}0[A-Za-z0-9]{6}");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan != null && PAN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC.matcher(ifsc).matches();
    }

    public static boolean isValidAmount(String amount) {
        if (amount == null)
            return false;
        try {
            long v = Long.parseLong(amount);
            return v > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
