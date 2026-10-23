# MiniBank Project — Part B (Practicals 1–14)

This folder contains the complete MiniBank console banking application built
cumulatively through Practicals 1–14 Part B. All code is in the `project details/MiniBank`
folder.

## Project Structure

```
project details/
└── MiniBank/
    ├── src/
    │   ├── annotation/          (Practical 7)
    │   │   ├── Id.java
    │   │   ├── MaxLength.java
    │   │   └── Positive.java
    │   ├── exception/           (Practical 8)
    │   │   ├── AccountNotFoundException.java
    │   │   ├── BankException.java
    │   │   ├── InsufficientFundsException.java
    │   │   └── InvalidAmountException.java
    │   ├── model/               (Practicals 1, 2, 4, 5, 6)
    │   │   ├── Account.java
    │   │   ├── BankInfo.java
    │   │   ├── Command.java
    │   │   ├── Customer.java
    │   │   ├── FixedDepositAccount.java
    │   │   ├── InterestBearing.java
    │   │   ├── MenuOption.java
    │   │   ├── Premium.java
    │   │   ├── SavingsAccount.java
    │   │   ├── Transactable.java
    │   │   ├── TransactionType.java
    │   │   └── WithdrawRule.java
    │   ├── service/             (Practical 14)
    │   │   └── BankService.java
    │   ├── ui/                  (Practicals 1, 14)
    │   │   └── MiniBank.java
    │   └── util/                (Practicals 4, 10, 11, 12, 13)
    │       ├── AccountStore.java
    │       ├── AnnotationValidator.java
    │       ├── CommandParser.java
    │       ├── ReportGenerator.java
    │       ├── Repository.java
    │       ├── StatementFormatter.java
    │       ├── StatePersister.java
    │       ├── TransactionLog.java
    │       └── TransactionProcessor.java
    ├── bin/                     (compiled .class files)
    └── README.md                (this file)
```

## Mapping to Practicals (Part B)

| Practical | Part B Feature | Files |
|-----------|---------------|-------|
| 1 | MiniBank menu shell (BankInfo, MenuOption) | `model/BankInfo.java`, `model/MenuOption.java`, `ui/MiniBank.java` |
| 2 | Customer & Account classes (encapsulation) | `model/Customer.java`, `model/Account.java` |
| 3 | toString/equals/hashCode on Account | `model/Account.java` |
| 4 | TransactionType, Command, Validator, CommandParser, StatementFormatter | `model/TransactionType.java`, `model/Command.java`, `util/Validator.java`, `util/CommandParser.java`, `util/StatementFormatter.java` |
| 5 | Account subclasses (Savings, Current, FixedDeposit) | `model/SavingsAccount.java`, `model/CurrentAccount.java`, `model/FixedDepositAccount.java` |
| 6 | Transactable, InterestBearing, WithdrawRule, Premium interfaces | `model/Transactable.java`, `model/InterestBearing.java`, `model/WithdrawRule.java`, `model/Premium.java` |
| 7 | @Id, @Positive, @MaxLength annotations + AnnotationValidator | `annotation/Id.java`, `annotation/Positive.java`, `annotation/MaxLength.java`, `util/AnnotationValidator.java` |
| 8 | Custom exception hierarchy (BankException, InsufficientFundsException, etc.) | `exception/BankException.java`, `exception/InsufficientFundsException.java`, `exception/AccountNotFoundException.java`, `exception/InvalidAmountException.java` |
| 9 | (Multithreading — Part A only, no Part B changes) | — |
| 10 | TransactionProcessor with thread pool + ConcurrentHashMap | `util/TransactionProcessor.java` |
| 11 | StatePersister (serialization), TransactionLog, ReportGenerator (NIO) | `util/StatePersister.java`, `util/TransactionLog.java`, `util/ReportGenerator.java` |
| 12 | AccountStore (HashMap, sorted by balance, transaction history) | `util/AccountStore.java` |
| 13 | Generic Repository<T> with @Id reflection + bounded type method | `util/Repository.java` |
| 14 | BankService capstone service layer + full MiniBank UI | `service/BankService.java`, `ui/MiniBank.java` |

## How to Compile

From the `project details/MiniBank` directory:

```cmd
javac -d bin src/model/*.java src/exception/*.java src/annotation/*.java src/util/*.java src/service/*.java src/ui/*.java
```

## How to Run

```cmd
java -cp bin ui.MiniBank
```

The program is menu-driven. Follow the on-screen prompts.

---

## Manual Testing Steps

### Test 1: Open a New Account
1. Run the program: `java -cp bin ui.MiniBank`
2. Choose option **1** (Open Account)
3. Enter account type: `1` (Savings), `2` (Current), or `3` (FixedDeposit)
4. Enter owner name (e.g., `Riya`)
5. Enter opening balance (e.g., `5000`)
6. If Savings: enter minimum balance (e.g., `1000`)
7. If Current: enter overdraft limit (e.g., `5000`)
8. Verify: Account number is displayed (e.g., `AC1001`)

### Test 2: Deposit Money
1. Choose option **2** (Deposit)
2. Enter the account number from Test 1
3. Enter amount (e.g., `1000`)
4. Verify: "Deposited 1000 to AC1001" message appears

### Test 3: Withdraw Money
1. Choose option **3** (Withdraw)
2. Enter the account number
3. Enter amount (e.g., `500`)
4. Verify: "Withdrew 500 from AC1001" message appears

### Test 4: View Account Statement
1. Choose option **5** (View Statement)
2. Enter the account number
3. Verify: Statement displays account number, owner, balance, active status, interest rate, yearly interest

### Test 5: List All Accounts
1. Choose option **6** (List All Accounts)
2. Verify: All accounts are listed with account number, owner, balance, and active status

### Test 6: List Accounts Sorted by Balance
1. Choose option **7** (List Accounts sorted by balance)
2. Verify: Accounts are listed in descending order by balance

### Test 7: Insufficient Funds (Savings Account)
1. Open a Savings account with balance 1000 and minBalance 500
2. Try to withdraw 600 (would leave 400, below minBalance of 500)
3. Verify: Error message "Insufficient funds" with shortfall amount

### Test 8: Fixed Deposit (Cannot Withdraw)
1. Open a FixedDeposit account
2. Try to withdraw any amount
3. Verify: Error message — withdrawal is not allowed

### Test 9: Invalid Amount
1. Try to deposit or withdraw a negative amount or zero
2. Verify: Error message "Invalid amount"

### Test 10: Account Not Found
1. Try to deposit/withdraw/view statement for a non-existent account number (e.g., `AC9999`)
2. Verify: Error message "Account not found"

### Test 11: Data Persistence (Save/Load)
1. Open one or more accounts
2. Choose option **10** (Exit) — accounts are saved to `accounts.dat`
3. Run the program again
4. Verify: "Loaded N account(s) from storage" message appears
5. Choose option **6** — previously created accounts are still there

### Test 12: End-of-Day Report
1. Perform some deposits and withdrawals (these write to `logs/transactions.log`)
2. Choose option **9** (Generate End-of-Day Report)
3. Verify: Report file `end_of_day_report.txt` is created with total deposits, withdrawals, and net change

### Test 13: Batch Transaction Processing
1. Ensure at least one account exists
2. Choose option **8** (Process Batch Transaction)
3. Verify: 5 concurrent deposits of 100 each are processed; balance increases correctly

### Test 14: Transfer Between Accounts
1. Open two accounts
2. Choose option **4** (Transfer)
3. Enter from account, to account, and amount
4. Verify: Transfer succeeds; both account balances updated correctly
5. Try transferring more than available balance — verify: "Insufficient funds" error

### Test 15: Invalid Menu Choice
1. Enter a number outside 1–10 or non-numeric input
2. Verify: "Invalid option" message and menu reappears

---

## Expected File Outputs After Testing

| File | Description |
|------|-------------|
| `accounts.dat` | Serialized accounts (saved on exit, loaded on start) |
| `logs/transactions.log` | Append-only transaction log |
| `end_of_day_report.txt` | End-of-day summary report |

---

## Notes

- All `.class` files are compiled into the `bin/` directory.
- Source files are in `src/` organized by package.
- The application uses Java 17+ features (records, switch expressions, var).
- Do **not** push to GitHub until you have verified the application works correctly.
