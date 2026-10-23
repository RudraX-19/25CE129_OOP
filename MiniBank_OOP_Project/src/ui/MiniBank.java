package ui;

import model.Account;
import model.BankInfo;
import model.MenuOption;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import service.BankService;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import exception.AccountNotFoundException;
import util.TransactionProcessor;
import util.ReportGenerator;
import util.StatePersister;
import util.TransactionLog;

import java.nio.file.Paths;
import java.util.Scanner;

/* ================================================================
   Practical 14 — Part B (MiniBank capstone UI)
   Connects the Practical 1 menu to BankService methods.
   Loads saved accounts on start-up, saves them on exit.
   Includes TransactionProcessor batch processing and report menu option.
   ================================================================ */

public class MiniBank {
    private static final BankService service = new BankService();
    private static final TransactionProcessor processor = new TransactionProcessor();
    private static final java.nio.file.Path ACCOUNTS_FILE =
            Paths.get("accounts.dat");
    private static final java.nio.file.Path LOGS_DIR =
            Paths.get("logs");
    private static final java.nio.file.Path LOG_FILE =
            Paths.get("logs", "transactions.log");
    private static final java.nio.file.Path REPORT_FILE =
            Paths.get("end_of_day_report.txt");

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // Load saved accounts on start-up (Practical 11 Part B)
        loadAccounts();

        // Ensure logs directory exists (Practical 11 Part B)
        java.nio.file.Files.createDirectories(LOGS_DIR);

        // Print header
        model.BankInfo bank = new model.BankInfo("Charotar University Bank", "CEUE203 Branch");
        System.out.println("========================================");
        System.out.println("Welcome to " + bank.name() + " — " + bank.branch());
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MiniBank Menu ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Statement");
            System.out.println("6. List All Accounts");
            System.out.println("7. List Accounts (sorted by balance)");
            System.out.println("8. Process Batch Transaction");
            System.out.println("9. Generate End-of-Day Report");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> openAccountFlow(sc);
                    case "2" -> depositFlow(sc);
                    case "3" -> withdrawFlow(sc);
                    case "4" -> transferFlow(sc);
                    case "5" -> statementFlow(sc);
                    case "6" -> listAccountsFlow(false);
                    case "7" -> listAccountsFlow(true);
                    case "8" -> batchProcessFlow(sc);
                    case "9" -> generateReportFlow();
                    case "10" -> running = false;
                    default -> System.out.println("Invalid option. Enter 1-10.");
                }
            } catch (BankException e) {
                System.out.println("Bank error: " + e.getMessage());
                if (e instanceof InsufficientFundsException ife) {
                    System.out.println("Shortfall: " + ife.getShortfall());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Save accounts on exit (Practical 11 Part B)
        saveAccounts();
        processor.stop();
        sc.close();
        System.out.println("Thank you for using MiniBank. Goodbye!");
    }

    /* ---------- Open Account ---------- */
    private static void openAccountFlow(Scanner sc) throws Exception {
        System.out.println("\n--- Open Account ---");
        System.out.print("Account type (1=Savings, 2=Current, 3=FixedDeposit): ");
        String type = sc.nextLine().trim();
        System.out.print("Owner name: ");
        String name = sc.nextLine().trim();
        System.out.print("Opening balance: ");
        long balance = Long.parseLong(sc.nextLine().trim());

        Account account;
        switch (type) {
            case "1" -> {
                System.out.print("Minimum balance: ");
                long minBal = Long.parseLong(sc.nextLine().trim());
                account = new SavingsAccount(name, balance, minBal);
            }
            case "2" -> {
                System.out.print("Overdraft limit: ");
                long odLimit = Long.parseLong(sc.nextLine().trim());
                account = new CurrentAccount(name, balance, odLimit);
            }
            case "3" -> account = new FixedDepositAccount(name, balance);
            default -> {
                System.out.println("Invalid account type.");
                return;
            }
        }
        service.openAccount(account);
        System.out.println("Account opened: " + account.getAccountNumber());
    }

    /* ---------- Deposit ---------- */
    private static void depositFlow(Scanner sc) throws Exception {
        System.out.print("Account number: ");
        String ac = sc.nextLine().trim();
        System.out.print("Amount: ");
        long amt = Long.parseLong(sc.nextLine().trim());
        service.deposit(ac, amt);
        TransactionLog.append(LOG_FILE, "DEPOSIT " + ac + " " + amt);
        System.out.println("Deposited " + amt + " to " + ac);
    }

    /* ---------- Withdraw ---------- */
    private static void withdrawFlow(Scanner sc) throws Exception {
        System.out.print("Account number: ");
        String ac = sc.nextLine().trim();
        System.out.print("Amount: ");
        long amt = Long.parseLong(sc.nextLine().trim());
        service.withdraw(ac, amt);
        TransactionLog.append(LOG_FILE, "WITHDRAW " + ac + " " + amt);
        System.out.println("Withdrew " + amt + " from " + ac);
    }

    /* ---------- Transfer ---------- */
    private static void transferFlow(Scanner sc) throws Exception {
        System.out.print("From account: ");
        String from = sc.nextLine().trim();
        System.out.print("To account: ");
        String to = sc.nextLine().trim();
        System.out.print("Amount: ");
        long amt = Long.parseLong(sc.nextLine().trim());
        service.transfer(from, to, amt);
        TransactionLog.append(LOG_FILE, "TRANSFER " + from + " -> " + to + " " + amt);
        System.out.println("Transferred " + amt + " from " + from + " to " + to);
    }

    /* ---------- Statement ---------- */
    private static void statementFlow(Scanner sc) throws Exception {
        System.out.print("Account number: ");
        String ac = sc.nextLine().trim();
        System.out.println(service.statement(ac));
    }

    /* ---------- List Accounts ---------- */
    private static void listAccountsFlow(boolean sorted) {
        System.out.println("\n--- Accounts ---");
        java.util.List<Account> accounts =
                sorted ? service.listAccountsSortedByBalance() : service.listAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts.");
            return;
        }
        for (Account a : accounts) {
            System.out.println(a);
        }
    }

    /* ---------- Batch Process ---------- */
    private static void batchProcessFlow(Scanner sc) {
        System.out.println("\n--- Batch Transaction Processing ---");
        System.out.println("Submitting sample concurrent deposits to processor...");
        Account a1 = service.listAccounts().stream().findFirst().orElse(null);
        if (a1 == null) {
            System.out.println("No accounts to process.");
            return;
        }
        final String acc = a1.getAccountNumber();
        System.out.println("Submitting sample concurrent deposits to processor...");
        for (int i = 0; i < 5; i++) {
            final long amount = 100;
            processor.submit(() -> {
                try {
                    service.deposit(acc, amount);
                } catch (Exception e) {
                    System.out.println("Batch task failed: " + e.getMessage());
                }
            });
        }
        processor.stop();
        System.out.println("Batch processed. New balance of " + acc + ": " + a1.getBalance());
    }

    /* ---------- Report ---------- */
    private static void generateReportFlow() throws Exception {
        java.nio.file.Files.createDirectories(LOGS_DIR);
        ReportGenerator.generate(LOGS_DIR, REPORT_FILE);
        System.out.println("Report generated: " + REPORT_FILE.toAbsolutePath());
    }

    /* ---------- Load / Save ---------- */
    private static void loadAccounts() throws Exception {
        if (java.nio.file.Files.exists(ACCOUNTS_FILE)) {
            Account[] loaded = StatePersister.load(ACCOUNTS_FILE);
            for (Account a : loaded) {
                service.loadAccount(a);
            }
            System.out.println("Loaded " + loaded.length + " account(s) from storage.");
        }
    }

    private static void saveAccounts() throws Exception {
        java.util.List<Account> all = service.listAccounts();
        StatePersister.save(all.toArray(new Account[0]), ACCOUNTS_FILE);
    }
}
