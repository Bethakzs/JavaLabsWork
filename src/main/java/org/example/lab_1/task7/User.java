package org.example.lab_1.task7;

import java.util.HashSet;
import java.util.Set;

public class User {
    private final String name;
    private final Set<BankAccount> accounts = new HashSet<>();

    public User(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<BankAccount> getAccounts() {
        return accounts;
    }

    public void addAccount(final BankAccount account) {
        for (BankAccount a : accounts) {
            if (a.getAccountNumber().equals(account.getAccountNumber())) {
                throw new IllegalArgumentException("Account number must be unique");
            }
        }
        accounts.add(account);
    }
}
