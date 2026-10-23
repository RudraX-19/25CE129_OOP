package exception;

/* ================================================================
   Practical 8 — Part B (MiniBank exception hierarchy)
   ================================================================
   BankException — checked base class for all bank exceptions.
   ================================================================ */

public class BankException extends Exception {
    private static final long serialVersionUID = 1L;

    public BankException(String message) {
        super(message);
    }
}
