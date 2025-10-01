import java.util.*;

class Account {
    private int accountNumber;
    private String accountHolderName;
    private String accountType;
    private double currentBalance;
    private List<String> transactionHistory;

    public Account(int accountNumber, String accountHolderName, String accountType, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.currentBalance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        recordTransaction("Account created with initial deposit: " + initialDeposit);
    }

    // Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    // Deposit method
    public void deposit(double amount) {
        currentBalance += amount;
        recordTransaction("Deposited: " + amount + " | New Balance: " + currentBalance);
    }

    // Withdraw method
    public boolean withdraw(double amount) {
        if (amount > currentBalance) {
            System.out.println("Insufficient balance!");
            return false;
        }
        currentBalance -= amount;
        recordTransaction("Withdrew: " + amount + " | New Balance: " + currentBalance);
        return true;
    }

    // Transfer method
    public boolean transfer(Account recipientAccount, double amount) {
        if (withdraw(amount)) {
            recipientAccount.deposit(amount);
            recordTransaction("Transferred " + amount + " to Account #" + recipientAccount.getAccountNumber());
            return true;
        }
        return false;
    }

    // Print account details
    public void printAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + accountHolderName);
        System.out.println("Account Type: " + accountType);
        System.out.println("Current Balance: " + currentBalance);
    }

    // Print transaction history
    public void printTransactionHistory() {
        System.out.println("---- Transaction History ----");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    private void recordTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}



class Bank {
    private Map<Integer, Account> accounts;
    private int nextAccountNumber; // starting account number

    public Bank() {
        accounts = new HashMap<>();
        nextAccountNumber = 1001; 
    }

    // Create new account
    public void createAccount(String accountHolderName, String accountType, double initialDeposit) {
        Account newAccount = new Account(nextAccountNumber, accountHolderName, accountType, initialDeposit);
        accounts.put(nextAccountNumber, newAccount);
        System.out.println("Account created successfully! Your Account Number: " + nextAccountNumber);
        nextAccountNumber++;
    }

    // Get account by account number
    public Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    // Display all accounts (optional for admin)
    public void displayAllAccounts() {
        for (Account account : accounts.values()) {
            account.printAccountDetails();
            System.out.println("---------------------------");
        }
    }
}



public class BankingManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\n--- Banking Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Account Details");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Account Holder Name: ");
                    String accountHolderName = scanner.nextLine();
                    System.out.print("Enter Account Type (Saving/Current): ");
                    String accountType = scanner.nextLine();
                    System.out.print("Enter Initial Deposit: ");
                    double initialDeposit = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    bank.createAccount(accountHolderName, accountType, initialDeposit);
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    int accountNumberDeposit = scanner.nextInt();
                    Account accountDeposit = bank.getAccount(accountNumberDeposit);
                    if (accountDeposit != null) {
                        System.out.print("Enter Amount to Deposit: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); // consume newline
                        accountDeposit.deposit(depositAmount);
                        System.out.println("Deposit Successful!");
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    int accountNumberWithdraw = scanner.nextInt();
                    Account accountWithdraw = bank.getAccount(accountNumberWithdraw);
                    if (accountWithdraw != null) {
                        System.out.print("Enter Amount to Withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine(); // consume newline
                        if (accountWithdraw.withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal Successful!");
                        }
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Your Account Number: ");
                    int fromAccountNumber = scanner.nextInt();
                    Account fromAccount = bank.getAccount(fromAccountNumber);
                    if (fromAccount == null) {
                        System.out.println("Account not found!");
                        continue;
                    }
                    System.out.print("Enter Receiver Account Number: ");
                    int toAccountNumber = scanner.nextInt();
                    Account toAccount = bank.getAccount(toAccountNumber);
                    if (toAccount == null) {
                        System.out.println("Receiver account not found!");
                        continue;
                    }
                    System.out.print("Enter Amount to Transfer: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    if (fromAccount.transfer(toAccount, transferAmount)) {
                        System.out.println("Transfer Successful!");
                    }
                    break;

                case 5:
                    System.out.print("Enter Account Number: ");
                    int accountNumberView = scanner.nextInt();
                    Account accountView = bank.getAccount(accountNumberView);
                    if (accountView != null) {
                        accountView.printAccountDetails();
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 6:
                    System.out.print("Enter Account Number: ");
                    int accountNumberTransactions = scanner.nextInt();
                    Account accountTransactions = bank.getAccount(accountNumberTransactions);
                    if (accountTransactions != null) {
                        accountTransactions.printTransactionHistory();
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 7:
                    System.out.println("Thank you for using the Banking Management System!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }
}