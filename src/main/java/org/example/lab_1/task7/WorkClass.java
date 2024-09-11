package org.example.lab_1.task7;

public class WorkClass {

    public static void main(String[] args) {
        try {
            final Bank bank1 = new Bank("Monobank");
            final Bank bank2 = new Bank("Privatbank");

            final User user1 = new User("Ivan");
            final User user2 = new User("Petro");

            final BankAccount account1 = new BankAccount("111111", bank1, "USD", 1000);
            final BankAccount account2 = new BankAccount("222222", bank1, "USD", 500);
            final BankAccount account3 = new BankAccount("333333", bank2, "EUR", 2000);
            final BankAccount account4 = new BankAccount("444444", bank2, "EUR", 1500);

            user1.addAccount(account1);
            user1.addAccount(account2);
            user2.addAccount(account3);
            user2.addAccount(account4);

            final BankTransaction transactionProcessor = new BankTransaction();

            // Given
            printUserAccountsBalance(user1);
            printUserAccountsBalance(user2);

            // When
            performTransfer(account1, account2, 100, transactionProcessor); // Same bank, same user
            performTransfer(account1, account3, 200, transactionProcessor); // Different bank, same user
            performTransfer(account1, account4, 50, transactionProcessor);  // Different bank, different users
            performTransfer(account3, account4, 300, transactionProcessor); // Same bank, different users

            // Then
            printUserAccountsBalance(user1);
            printUserAccountsBalance(user2);

        } catch (IllegalArgumentException e) {
            System.err.println("Error : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unknown error : " + e.getMessage());
        }
    }

    private static void printUserAccountsBalance(User user) {
        System.out.println("User: " + user.getName());
        for (BankAccount account : user.getAccounts()) {
            System.out.println("Balance Account " + account.getAccountNumber() + " : " + account.getBalance() + " " + account.getCurrency());
        }
    }

    private static void performTransfer(BankAccount fromAccount, BankAccount toAccount, double amount, BankTransaction transactionProcessor) {
        if (transactionProcessor.transfer(fromAccount, toAccount, amount)) {
            System.out.println("Transfer of " + amount + " " + fromAccount.getCurrency() + " from account " + fromAccount.getAccountNumber() +
                    " to account " + toAccount.getAccountNumber() + " was successful");
        } else {
            System.out.println("Transfer of " + amount + " " + fromAccount.getCurrency() + " from account " + fromAccount.getAccountNumber() +
                    " to account " + toAccount.getAccountNumber() + " failed");
        }
    }
}
