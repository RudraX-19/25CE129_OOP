package exception;

/* ================================================================
   Practical 8 — Part B (InvalidAmountException)
   ================================================================ */

public class InvalidAmountException extends BankException {
    private static final long serialVersionUID = 1L;

    public InvalidAmountException(long amount) {
        super("Invalid amount: " + amount + ". Amount must be greater than zero.");
    }
}
