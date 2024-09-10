package org.example.lab_1.task7;

public class WorkClass {

    public static void main(String[] args) {
        try {
            final Bank bank1 = new Bank("Monobank");
            final Bank bank2 = new Bank("Privatbank");

            final BankAccount account1 = new BankAccount("111111", bank1, "USD", 1000);
            final BankAccount account2 = new BankAccount("222222", bank1, "USD", 500);
            final BankAccount account3 = new BankAccount("333333", bank2, "EUR", 2000);
            final BankAccount account4 = new BankAccount("444444", bank2, "EUR", 1500);

            final BankTransaction transactionProcessor = new BankTransaction();

            //given
            printAccountBalance(account1, account2, account3, account4);

            //when
            performTransfer(account1, account2, 100, transactionProcessor); // In the same bank, the same user
            performTransfer(account1, account3, 200, transactionProcessor); // In different banks, the same user
            performTransfer(account1, account4, 50, transactionProcessor);  // In the same bank, different users
            performTransfer(account3, account4, 300, transactionProcessor); // In different banks, different users

            //then
            printAccountBalance(account1, account2, account3, account4);

        } catch (IllegalArgumentException e) {
            System.out.println("Error : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unknown error : " + e.getMessage());
        }
    }

    private static void printAccountBalance(BankAccount account1, BankAccount account2, BankAccount account3, BankAccount account4) {
        System.out.println("Balance Account1 : " + account1.getBalance() + " " + account1.getCurrency());
        System.out.println("Balance Account2 : " + account2.getBalance() + " " + account2.getCurrency());
        System.out.println("Balance Account3 : " + account3.getBalance() + " " + account3.getCurrency());
        System.out.println("Balance Account4 : " + account4.getBalance() + " " + account4.getCurrency());
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
