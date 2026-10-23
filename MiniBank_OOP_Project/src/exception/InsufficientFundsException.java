package exception;

/* ================================================================
   Practical 8 — Part B (InsufficientFundsException)
   Carries the shortfall amount.
   ================================================================ */

public class InsufficientFundsException extends BankException {
    private static final long serialVersionUID = 1L;
    private final long shortfall;

    public InsufficientFundsException(String accountNumber, long amount, long balance) {
        super("Insufficient funds in account " + accountNumber
                + ": requested " + amount + ", available " + balance);
        this.shortfall = amount - balance;
    }

    public long getShortfall() {
        return shortfall;
    }
}
