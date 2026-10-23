package util;

import model.Account;

/* ================================================================
   Practical 4 — Part B (StatementFormatter)
   Builds a multi-line account statement string using StringBuilder.
   ================================================================ */

public class StatementFormatter {
    public static String buildStatement(Account account) {
        if (account == null) {
            return "Account not found.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("===== Account Statement =====\n");
        sb.append("Account Number : ").append(account.getAccountNumber()).append("\n");
        sb.append("Owner Name     : ").append(account.getOwnerName()).append("\n");
        sb.append("Balance        : ").append(String.format("%,d", account.getBalance())).append("\n");
        sb.append("Active         : ").append(account.isActive()).append("\n");
        sb.append("Interest Rate  : ").append(account.interestRate()).append("%\n");
        sb.append("Yearly Interest: ").append(String.format("%.2f", account.yearlyInterest())).append("\n");
        sb.append("=============================\n");
        return sb.toString();
    }
}
