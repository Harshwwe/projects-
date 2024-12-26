import java.util.ArrayList;
import java.util.Scanner;

// Represents a single transaction (deposit, withdrawal, transfer)
class Transaction {
    String type; // Type of transaction (e.g., "Deposit", "Withdrawal")
    double amount; // Amount of the transaction

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount; // Nicely formatted transaction string
    }
}

// Represents a bank account
class Account {
    int userId; // User's ID
    int pin; // User's PIN
    double balance; // Account balance
    ArrayList<Transaction> transactionHistory; // List of transactions

    public Account(int userId, int pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>(); // Initialize transaction history
    }

    // Deposits money into the account
    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount)); // Record the transaction
    }

    // Withdraws money from the account
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount)); // Record the transaction
            return true; // Withdrawal successful
        }
        return false; // Insufficient funds
    }

    // Transfers money to another account
    public boolean transfer(Account recipient, double amount) {
        if (withdraw(amount)) { // Withdraw from this account first
            recipient.deposit(amount); // Deposit into the recipient's account
            transactionHistory.add(new Transaction("Transfer to " + recipient.userId, amount)); // Record the transaction
            return true; // Transfer successful
        }
        return false; // Transfer failed (insufficient funds)
    }

    // Prints the transaction history
    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactionHistory) {
                System.out.println("- " + transaction); // Print each transaction
            }
        }
    }
}

// Main ATM class
public class ATM_interface {
    private static Account currentAccount; // The currently logged-in account
    private static Scanner scanner = new Scanner(System.in); // For user input
    private static ArrayList<Account> accounts = new ArrayList<>(); // List of all accounts

    public static void main(String[] args) {
        // Initialize some accounts for testing
        accounts.add(new Account(1234, 1234, 1000));
        accounts.add(new Account(5678, 5678, 500));

        System.out.println("Welcome to the Friendly Neighborhood ATM!"); // More welcoming message
        login(); // Start the login process
        if (currentAccount != null) { // If login was successful
            showMenu(); // Show the main menu
        }
        System.out.println("Thank you for banking with us!"); // Farewell Message
    }

    // Handles user login
    private static void login() {
        System.out.print("Please enter your User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Please enter your PIN: ");
        int pin = scanner.nextInt();

        // Search for the account
        for (Account account : accounts) {
            if (account.userId == userId && account.pin == pin) {
                currentAccount = account;
                System.out.println("Login successful! Welcome, User " + userId + "!"); // Personalized welcome
                return;
            }
        }

        System.out.println("Invalid credentials. Please try again.");
        login(); // Try logging in again
    }

    // Displays the main ATM menu and handles user choices
    private static void showMenu() {
        while (true) {
            System.out.println("\nWhat would you like to do today?"); // Friendlier prompt
            System.out.println("1. View Transaction History");
            System.out.println("2. Make a Withdrawal");
            System.out.println("3. Make a Deposit");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentAccount.printTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter the amount you wish to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    if (currentAccount.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful. Please take your cash.");
                    } else {
                        System.out.println("Insufficient funds. Please check your balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the amount you wish to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                    System.out.println("Deposit successful. Your balance has been updated.");
                    break;
                case 4:
                    System.out.print("Enter the recipient's User ID: ");
                    int recipientId = scanner.nextInt();
                    Account recipient = null;
                    for (Account acc : accounts) {
                        if (acc.userId == recipientId) {
                            recipient = acc;
                            break;
                        }
                    }
                    if (recipient == null) {
                        System.out.println("Recipient account not found.");
                        break;
                    }
                    System.out.print("Enter the amount you wish to transfer: $");
                    double transferAmount = scanner.nextDouble();
                    if (currentAccount.transfer(recipient, transferAmount)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed (insufficient funds).");
                    }
                    break;
                case 5:
                    System.out.println("Exiting ATM. Have a great day!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}